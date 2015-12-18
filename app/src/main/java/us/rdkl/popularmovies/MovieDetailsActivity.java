package us.rdkl.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_container, new PlaceholderFragment())
                    .commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final String LOG_TAG = this.getClass().getSimpleName();

            View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

            Intent intent = getActivity().getIntent();
            Movie movie = (Movie) intent.getSerializableExtra("movie"); // TODO use a db instead and pass DB index of selected item

            TextView field = (TextView) rootView.findViewById(R.id.detail_movie_title);
            field.setText(movie.getTitle());

            field = (TextView) rootView.findViewById(R.id.detail_movie_rating);
            field.setText(movie.getRatingString());

            field = (TextView) rootView.findViewById(R.id.detail_movie_release_date);
            field.setText(movie.getReleaseDateString());

            field = (TextView) rootView.findViewById(R.id.detail_movie_plot);
            field.setText(movie.getPlot());

            ImageView poster = (ImageView) rootView.findViewById(R.id.detail_movie_poster_image);
            Picasso.with(rootView.getContext()).load(movie.getPosterUrl()).into(poster);

            return rootView;
        }
    }

}
