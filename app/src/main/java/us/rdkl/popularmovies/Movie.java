package us.rdkl.popularmovies;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by russ on 12/13/15.
 */
// TODO implement Parcelable
public class Movie implements Map<String, String>, Serializable {
    private String mTitle;
    private String mPosterUrl;
    private String mPlot;
    private Double mRating;
    private Date mReleaseDate;

    private final String[] KEYS = {"title", "posterUrl", "plot", "rating", "releaseDate"};

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
    public void clear() {
        mTitle = null;
        mReleaseDate = null;
        mPosterUrl = null;
        mPlot = null;
        mRating = null;
    }

    @Override
    public boolean containsKey(Object key) {
        List<String> keys = new ArrayList<String>();
        for(String k : KEYS) { keys.add(k); }

        return keys.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return getTitle().equals(value) ||
            getReleaseDate().equals(value) ||
            getPlot().equals(value) ||
            getRating().equals(value) ||
            getPosterUrl().equals(value);
    }

    @NonNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        // Stub only
        return null;
    }

    @Override
    public String get(Object key) {
        if(key.equals("title")) { return getTitle(); }
        else if(key.equals("releaseDate")) { return getReleaseDateString(); }
        else if(key.equals("plot")) { return getPlot(); }
        else if(key.equals("posterUrl")) { return getPosterUrl(); }
        else if(key.equals("rating")) { return getRatingString(); }
        else return null;
    }

    @Override
    public int hashCode() {
        return (getTitle() + getPlot() + getReleaseDate().toString()).hashCode();
    }

    @Override
    public boolean isEmpty() {
        return getTitle().equals(null) &&
                getReleaseDate().equals(null) &&
                getPlot().equals(null) &&
                getRating().equals(null) &&
                getPosterUrl().equals(null);
    }

    @NonNull
    @Override
    public Set<String> keySet() {
        Set<String> setKeys = new HashSet<String>();
        for(String key : KEYS) { setKeys.add(key); }
        return setKeys;
    }

    @Override
    public String put(String key, String value) {
        boolean match = false;

        if(key.equals("title")) { setTitle(value); match = true;}
        else if(key.equals("releaseDate")) { setReleaseDate(value); match = true; }
        else if(key.equals("plot")) { setPlot(value); match = true; }
        else if(key.equals("posterUrl")) { setPosterUrl(value); match = true; }
        else if(key.equals("rating")) { setRating(value); match = true; }

        if(match) { return value; }
        else { return null; }
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> map) {
        // Stub only
    }

    @Override
    public String remove(Object key) {
        String ret = null;

        if(key.equals("title")) { ret = getTitle(); mTitle = null; }
        else if(key.equals("releaseDate")) { ret = getReleaseDateString(); mReleaseDate = null; }
        else if(key.equals("plot")) { ret = getPlot(); mPlot = null; }
        else if(key.equals("posterUrl")) { ret = getPosterUrl(); mPosterUrl = null; }
        else if(key.equals("rating")) { ret = getRatingString(); mRating = null; }

        return ret;
    }

    @Override
    public int size() {
        return KEYS.length;
    }

    @NonNull
    @Override
    public Collection<String> values() {
        Collection<String> ret = new ArrayList<String>();

        ret.add(getTitle());
        ret.add(getReleaseDateString());
        ret.add(getPlot());
        ret.add(getPosterUrl());
        ret.add(getRatingString());

        return ret;
    }
}
