package example.sb.model;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Representao um post completo no sistema...
 *
 * Created by soldier on 9/21/15.
 */
@Table(name = "Post", id = BaseColumns._ID)
public class Post extends Model {
    public static final String ACTION_ID = "actionId";
    public static final String BODY = "body";

    @Column(name = ACTION_ID)
    @SerializedName(ACTION_ID)
    private int actionId;

    @Column(name = BODY)
    @SerializedName(BODY)
    private String body;

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
