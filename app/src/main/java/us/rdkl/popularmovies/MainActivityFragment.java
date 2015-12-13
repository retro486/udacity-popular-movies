package us.rdkl.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {
        private final String LOG_TAG = this.getClass().getSimpleName();

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
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
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            if(movies != null) {
                mMovieAdapter.clear();
                mMovieAdapter.addAll(movies);
            }
        }
    }
}
