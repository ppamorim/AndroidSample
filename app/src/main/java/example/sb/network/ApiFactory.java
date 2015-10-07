package example.sb.network;

import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import example.sb.BuildConfig;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;

/**
 * Creates ou APIs abstraction to communicate with server.
 *
 * Created by ghost on 06/09/15.
 */
public class ApiFactory {
    private static final ApiFactory instance = new ApiFactory();
    private static Api api;
    public static final String TOKEN = "eyJ0eXAiOiJKV1MiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1NjAzMjM0YmNkMDU3IiwiaXNzIjoiaHR0cHM6XC9cL3AtYXBwLnNvY2lhbGJhc2UuY29tLmJyXC8iLCJhdWQiOiJodHRwczpcL1wvc29jaWFsYmFzZS5zb2NpYWxiYXNlLmNvbS5iciIsInVzZXIiOnsidXNlcm5hbWUiOiJ5dXJpQHNvY2lhbGJhc2UuY29tLmJyIn0sIm5ldHdvcmsiOnsibmFtZSI6IlNvY2lhbGJhc2UiLCJyZXNvdXJjZSI6InNiLXNvY2lhbGJhc2UiLCJrZXkiOiI0NDFiZjA5ZDk3NjJhYWE3ZDA0NDExYTZhYzUyMDkwMSIsInR5cGUiOiJDTElFTlQifSwiZXhwIjoxNDQzMDYwNjE5fQ.ZY3obxt1Jf5Yn9WGHCbDCS44xydOb_PPw-89mFrsGqA";

    private ApiFactory() {}

    public static ApiFactory getInstance() {
        return instance;
    }

    public Api getApi() {
        if(api == null) {
            api = new RestAdapter.Builder()
                    .setEndpoint(BuildConfig.API_URL)
                    .setRequestInterceptor(new SendInterceptor())
                    .setErrorHandler(new HandleError())
                    .setConverter(new GsonConverter(new GsonBuilder()
                            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                            .serializeNulls()
                            .create()))
                    .build()
                    .create(Api.class);
        }
        return api;
    }

    /**
     * General handling before sending a request.
     */
    private static class SendInterceptor implements RequestInterceptor {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Accept", "application/json");
            request.addHeader("User-Agent", "SocialBase/Android");
            request.addHeader("Origin", "https://socialbase.socialbase.com.br");
            request.addHeader("Authorization", "Bearer " + TOKEN);
        }
    }

    public static class HandleError implements ErrorHandler {
        @Override
        public Throwable handleError(RetrofitError cause) {
            if(cause.getCause() != null)
                cause.getCause().printStackTrace();
            System.out.println(
                    new String(((TypedByteArray) cause.getResponse().getBody()).getBytes()));
            return null;
        }
    }
}
