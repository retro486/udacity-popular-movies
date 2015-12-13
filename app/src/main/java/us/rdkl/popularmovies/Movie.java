package us.rdkl.popularmovies;

import java.util.Date;

/**
 * Created by russ on 12/13/15.
 */
public class Movie {
    private String mTitle;
    private String mPosterUrl;
    private String mPlot;
    private Double mRating;
    private Date mReleaseDate;

    public Movie(String title, String posterUrl, String plot, Double rating, Date releaseDate) {
        mTitle = title;
        mPosterUrl = posterUrl;
        mPlot = plot;
        mRating = rating;
        mReleaseDate = releaseDate;
    }
}
