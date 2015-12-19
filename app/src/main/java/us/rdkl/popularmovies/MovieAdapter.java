// Based on https://nickcharlton.net/posts/building-custom-android-listviews.html
package us.rdkl.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by russ on 12/16/15.
 */
public class MovieAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int resource;
    private List<Movie> data;

    public MovieAdapter(Context context, int resource, List<Movie> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.data = data;
    }

    public int getCount() {
        return this.data.size();
    }

    /**
     * Return an object in the data set.
     */
    public Object getItem(int position) {
        return this.data.get(position);
    }

    /**
     * Return the position provided.
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Return a generated view for a position.
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // reuse a given view, or inflate a new one from the xml
        View view;

        if (convertView == null) {
            view = this.inflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        // bind the data to the view object
        return this.bindData(view, position);
    }

    /**
     * Bind the provided data to the view.
     * This is the only method not required by base adapter.
     */
    public View bindData(View view, int position) {
        if (this.data.get(position) == null) {
            return view;
        }

        Movie item = this.data.get(position);

        TextView field = (TextView) view.findViewById(R.id.movie_title);
        field.setText(item.getTitle());

        field = (TextView) view.findViewById(R.id.movie_rating);
        if(item.getRating() == null) {
            field.setVisibility(View.GONE);
        } else {
            field.setText(item.getRatingString());
        }

        field = (TextView) view.findViewById(R.id.movie_release_date);
        if(item.getReleaseDate() == null) {
            field.setVisibility(View.GONE);
        } else {
            field.setText(item.getReleaseDateString());
        }

        field = (TextView) view.findViewById(R.id.movie_plot);
        field.setText(item.getPlot());

        ImageView poster = (ImageView) view.findViewById(R.id.movie_poster_image);
        Picasso.with(view.getContext()).load(item.getPosterUrl()).into(poster);

        return view;
    }

    public void updateDataSet(List<Movie> results) {
        this.data = results;
        notifyDataSetChanged();
    }
}
