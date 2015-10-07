package example.sb.ui.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.TextView;

import example.sb.R;
import example.sb.model.Post;

/**
 * This adapter should list items.
 *
 * Created by ghost on 06/09/15.
 */
public class PostAdapter
        extends RecyclerView.Adapter<PostAdapter.ViewHolder> implements Filterable {
    private Cursor data;
    private FilterQueryProvider filterQueryProvider;

    public PostAdapter(Cursor data, FilterQueryProvider filterQueryProvider) {
        this.data = data;
        this.filterQueryProvider = filterQueryProvider;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View contentView = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.adapter_catalog_item, viewGroup, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        data.moveToPosition(i);
        Post post = new Post();
        post.loadFromCursor(data);

        holder.lText.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return data.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView lAuthor;
        public TextView lText;

        public ViewHolder(View itemView) {
            super(itemView);
            lAuthor = (TextView) itemView.findViewById(R.id.lAuthor);
            lText = (TextView) itemView.findViewById(R.id.lText);
        }
    }

    public void changeCursor(Cursor cursor) {
        if (cursor == data)
            return;
        Cursor old = data;
        data = cursor;

        if(old != null) {
            old.close();
        }

        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Cursor newCuror = filterQueryProvider.runQuery(constraint);
                FilterResults filterResults = new FilterResults();
                filterResults.count = newCuror.getCount();
                filterResults.values = newCuror;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                changeCursor((Cursor) results.values);
            }
        };
    }
}
