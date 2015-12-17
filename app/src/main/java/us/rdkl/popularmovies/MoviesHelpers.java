package us.rdkl.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by russ on 12/13/15.
 */
public class MoviesHelpers {
    public static String getMovieData(String sortBy) {
        final String LOG_TAG = "MoviesHelpers.getMovieData";

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;
        int numDays = 7;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            Uri.Builder builder = new Uri.Builder();
            builder
                    .scheme("http")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("discover")
                    .appendPath("movie")
                    .appendQueryParameter("sort_by", sortBy) // popularity.desc or vote_average.desc
                    .appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_KEY);

            URL url = new URL(builder.build().toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                Log.i(LOG_TAG, "No data returned.");
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                Log.i(LOG_TAG, "No data to read.");
                return null;
            }
            forecastJsonStr = buffer.toString();

            return forecastJsonStr;

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }

    public static List<Movie> processMovieJSON(String jsonString)
            throws JSONException {
        final String LOG_TAG = "MovieHelpers.processMovieJSON";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Movie> movies = new ArrayList<Movie>();

        JSONObject data = new JSONObject(jsonString);
        JSONArray results = data.getJSONArray("results");

        for(int i = 0; i < results.length(); i++) {
            JSONObject obj = results.getJSONObject(i);
            String title;
            String posterUrl;
            String plot;
            Double rating;
            Date releaseDate;

            title = obj.getString("original_title");
            posterUrl = obj.getString("poster_path"); // TODO prefix
            plot = obj.getString("overview");
            rating = obj.getDouble("vote_average");
            try {
                releaseDate = sdf.parse(obj.getString("release_date"));
            } catch(java.text.ParseException e) {
                releaseDate = null;
            }

            Movie movie = new Movie(title, posterUrl, plot, rating, releaseDate);
            movies.add(movie);
        }

        return movies;

    }
}
