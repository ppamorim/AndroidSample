package example.sb.ui.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FilterQueryProvider;

import com.activeandroid.Cache;
import com.activeandroid.query.Select;

import example.sb.R;
import example.sb.model.Post;
import example.sb.ui.adapter.PostAdapter;

/**
 * This a list of items.
 */
public class PostFragment extends Fragment implements SearchView.OnQueryTextListener {
    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_catalog, container, false);
        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.my_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        updateList();
        return contentView;
    }

    /**
     * Loads data to init list.
     */
    public void updateList() {
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params) {
                String sql = new Select().from(Post.class).toSql();
                return Cache.openDatabase().rawQuery(sql, null);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                mAdapter = new PostAdapter(cursor, filter);
                mRecyclerView.setAdapter(mAdapter);
            }
        }.execute();
    }

    private FilterQueryProvider filter = new FilterQueryProvider() {
        @Override
        public Cursor runQuery(CharSequence constraint) {
            String[] selection = new String[]{ "%" + constraint.toString() + "%" };
            String sql = new Select().from(Post.class).where(Post.BODY + " like ?").toSql();
            return Cache.openDatabase().rawQuery(sql, selection);
        }
    };

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return true;
    }
}
