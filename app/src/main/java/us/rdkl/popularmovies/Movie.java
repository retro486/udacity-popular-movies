package us.rdkl.popularmovies;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by russ on 12/13/15.
 */
// TODO move this into a DB
public class Movie implements Serializable {
    private String mTitle;
    private String mPosterUrl;
    private String mPlot;
    private Double mRating;
    private Date mReleaseDate;

    public Movie() {}

    public Movie(String title, String posterUrl, String plot, Double rating, Date releaseDate) {
        mTitle = title;
        mPosterUrl = posterUrl;
        mPlot = plot;
        mRating = rating;
        mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPlot() {
        return mPlot;
    }

    public void setPlot(String plot) {
        mPlot = plot;
    }

    public Double getRating() {
        return mRating;
    }

    public void setRating(Double rating) {
        mRating = rating;
    }

    public void setRating(String rating) {
        mRating = Double.parseDouble(rating);
    }

    public String getRatingString() {
        return getRating().toString();
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        mReleaseDate = releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            setReleaseDate(sdf.parse(releaseDate));
        } catch(ParseException e) {
            return; // Don't change the release date.
        }
    }

    public String getReleaseDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
        return sdf.format(getReleaseDate());
    }

    public String getPosterUrl() {
        // TODO prefix url
        return mPosterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        mPosterUrl = posterUrl; // TODO poster prefix
    }

    @Override
    public String toString() {
        return mTitle + " (" + mReleaseDate.toString() + ")";
    }

    public boolean equals(Movie m) {
        return mTitle == m.getTitle() && mReleaseDate == m.getReleaseDate();
    }

    @Override
    public int hashCode() {
        return (getTitle() + getPlot() + getReleaseDate().toString()).hashCode();
    }
}
