package example.sb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Empactodar de Json padronizado utilizado pelo API.
 *
 * Created by soldier on 9/22/15.
 */
public class ItemsWrapper<T> {
    @SerializedName("count")
    private int count;
    @SerializedName("items")
    private List<T> items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
