package com.udacity.fasttrack.popularmovies.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codedentwickler on 4/9/17.
 */

public class MoviesList {

    @SerializedName("results")
    @Expose
    private List<Movie> movies = new ArrayList<>();

    @SerializedName("total_results")
    private long totalResults;

    @SerializedName("total_pages")
    private int totalPages;


    public List<Movie> getMovies() {
        return movies;
    }


    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }


}
