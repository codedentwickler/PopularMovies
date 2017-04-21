package com.udacity.fasttrack.popularmovies.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by codedentwickler on 4/21/17.
 */

public class TrailersList {

    @SerializedName("id")
    private long movieId;

    @SerializedName("results")
    @Expose
    private ArrayList<Trailer> trailers;

    public TrailersList(long movieId, ArrayList<Trailer> trailers) {
        this.movieId = movieId;
        this.trailers = trailers;
    }

    public long getMovieId() {
        return movieId;
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

}
