package example.sb.business;

import com.activeandroid.ActiveAndroid;

import org.json.JSONException;

import java.util.List;

import example.sb.model.ItemsWrapper;
import example.sb.model.Post;
import example.sb.network.Api;
import example.sb.network.ApiFactory;

/**
 * Regra de negocio relacionado ao {@link Post}
 *
 * Created by ghost on 06/09/15.
 */
public class PostBusiness {
    /**
     * Update all our product database from server.
     */
    public void updateAll() {
        Api api = ApiFactory.getInstance().getApi();

        try {
            ItemsWrapper<Post> items = api.activities();
            List<Post> posts = items.getItems();
            updatePosts(posts);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updatePosts(List<Post> posts) throws JSONException {
        try {
            ActiveAndroid.beginTransaction();
            for (Post it : posts) {
                it.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
