package us.rdkl.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private SimpleAdapter mMovieAdapter;
    private List<Movie> mMovieList;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mMovieList = new ArrayList<Movie>();
        Movie templateMovie = new Movie();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        String[] from = {"posterUrl", "title", "releaseDate", "rating"}; // Hardcoded to ensure order
        int[] to = {R.id.movie_poster_image, R.id.movie_title, R.id.movie_release_date, R.id.movie_rating};
        mMovieAdapter = new SimpleAdapter(getContext(), (List<? extends Map<String, ?>>) mMovieList, R.layout.list_item_movie, from, to);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_movies);
        listView.setAdapter(mMovieAdapter);

        return rootView;
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
        private final String LOG_TAG = this.getClass().getSimpleName();

        @Override
        protected List<Movie> doInBackground(String... params) {
            if(params.length != 2) {
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
                mMovieList = movies;
                mMovieAdapter.notifyDataSetChanged();
            }
        }
    }

    public void reloadMovies() {
        // TODO grab sort from config
        new FetchMoviesTask().execute("popularity.desc");
    }

    @Override
    public void onStart() {
        super.onStart();
        reloadMovies();
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
