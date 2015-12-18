package us.rdkl.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMovieList = new ArrayList<Movie>();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mMovieAdapter = new MovieAdapter(getContext(), R.layout.list_item_movie, mMovieList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_movies);
        listView.setAdapter(mMovieAdapter);

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = (Movie) adapterView.getItemAtPosition(position);
                Intent showMovieIntent = new Intent(getActivity(), MovieDetailsActivity.class);
//                showMovieIntent.putExtra("movie", movie);
                startActivity(showMovieIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        reloadMovies();
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
        private final String LOG_TAG = this.getClass().getSimpleName();

        @Override
        protected List<Movie> doInBackground(String... params) {
            if(params.length != 1) {
                return null;
            }

            String jsonData = MoviesHelpers.getMovieData(params[0]);

            try {
                return MoviesHelpers.processMovieJSON(jsonData);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error parsing server JSON", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);

            if(movies != null) {
                mMovieAdapter.updateDataSet(movies);
            }
        }
    }

    public void reloadMovies() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String sort = prefs.getString(getString(R.string.sort_pref_key), "popularity.desc");

        new FetchMoviesTask().execute(sort);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movie, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            reloadMovies();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
