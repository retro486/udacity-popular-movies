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

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ArrayAdapter<Movie> mMovieAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        List<Movie> movies = new ArrayList<Movie>();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mMovieAdapter = new ArrayAdapter<Movie>(
                getActivity(), // The current context (this activity)
                R.layout.fragment_main, // The name of the layout ID.
                movies);

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
            ArrayList<Movie> movies;

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
                mMovieAdapter.clear();
                mMovieAdapter.addAll(movies);
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
