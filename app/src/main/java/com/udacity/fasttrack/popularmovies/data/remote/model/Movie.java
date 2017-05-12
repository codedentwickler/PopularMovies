package com.udacity.fasttrack.popularmovies.data.remote.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.COLUMN_NAME_BACKDROP_URL;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.COLUMN_NAME_ID;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.COLUMN_NAME_ORIGINAL_TITLE;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.COLUMN_NAME_OVERVIEW;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.COLUMN_NAME_POSTER_URL;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.COLUMN_NAME_RELEASE_DATE;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.COLUMN_NAME_VOTE_AVERAGE;

/**
 * Created by codedentwickler on 4/9/17.
 */

public class Movie implements Parcelable {

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds;

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    public Movie(String posterPath,
                 String overview,
                 String releaseDate,
                 long id,
                 String originalTitle,
                 String backdropPath,
                 Double voteAverage) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
    }

    public static Movie parseCursorToMovie(Cursor cursor){
        String posterPath = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_POSTER_URL));
        String overview = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OVERVIEW));
        String releaseDate = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_RELEASE_DATE));
        long id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
        String originalTitle = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ORIGINAL_TITLE));
        String backdropPath = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_BACKDROP_URL));
        Double voteAverage = cursor.getDouble(cursor.getColumnIndex(COLUMN_NAME_VOTE_AVERAGE));

        return new Movie(posterPath, overview, releaseDate, id, originalTitle, backdropPath, voteAverage);
    }


    public Movie(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ContentValues toFavoritesContentValues(@NonNull String localPosterPath,
                                                  @NonNull String localBackdropPath) {
        ContentValues values = new ContentValues();
        values.put(FavoriteEntry.COLUMN_NAME_ID, this.id);
        values.put(COLUMN_NAME_POSTER_URL, this.posterPath);
        values.put(FavoriteEntry.COLUMN_NAME_BACKDROP_URL, this.backdropPath);
        values.put(FavoriteEntry.COLUMN_NAME_ORIGINAL_TITLE, this.originalTitle);
        values.put(FavoriteEntry.COLUMN_NAME_OVERVIEW, this.overview);
        values.put(FavoriteEntry.COLUMN_NAME_RELEASE_DATE, this.releaseDate);
        values.put(FavoriteEntry.COLUMN_NAME_VOTE_AVERAGE, this.voteAverage);
        values.put(FavoriteEntry.COLUMN_NAME_LOCAL_POSTER_URL, localPosterPath);
        values.put(FavoriteEntry.COLUMN_NAME_LOCAL_BACKDROP_URL, localBackdropPath);

        return values;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.originalTitle);
        dest.writeList(this.genreIds);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeDouble(this.voteAverage);
    }

    protected Movie(Parcel in) {
        this.id = in.readLong();
        this.originalTitle = in.readString();
        this.genreIds = new ArrayList<>();
        in.readList(this.genreIds, List.class.getClassLoader());
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.voteAverage = in.readDouble();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {return new Movie(source);}

        public Movie[] newArray(int size) {return new Movie[size];}
    };
}
