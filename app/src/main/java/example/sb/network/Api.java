package example.sb.network;

import example.sb.model.ItemsWrapper;
import example.sb.model.Post;
import retrofit.http.GET;

/**
 * Main API to sync with server.
 *
 * Created by ghost on 06/09/15.
 */
public interface Api {
    @GET("/activities")
    ItemsWrapper<Post> activities();
}
