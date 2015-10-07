package example.sb.ui.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import example.sb.R;
import example.sb.business.PostBusiness;
import example.sb.ui.fragment.PostFragment;

/**
 * This activity shows an item catalog.
 */
public class PostActivity extends AppCompatActivity {
    private PostFragment postFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        postFragment = (PostFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragCatalog);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);

        MenuItem searchItem = menu.findItem(R.id.mSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(postFragment);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mSync) {
            sync();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This is a simple example, should not sync on activity, try a Service.
     */
    private void sync() {
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(
                        PostActivity.this, "", getString(R.string.loading));
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    new PostBusiness().updateAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
                postFragment.updateList();
            }
        }.execute();
    }
}
