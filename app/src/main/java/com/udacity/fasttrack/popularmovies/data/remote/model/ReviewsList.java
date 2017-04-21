package com.udacity.fasttrack.popularmovies.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by codedentwickler on 4/21/17.
 */

public class ReviewsList {

    @SerializedName("id")
    private long movieId;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<Review> reviews;

    @SerializedName("total_pages")
    private int totalPages;

    public ReviewsList(long movieId, int page, ArrayList<Review> reviews, int totalPages) {
        this.movieId = movieId;
        this.page = page;
        this.reviews = reviews;
        this.totalPages = totalPages;
    }

    public long getMovieId() {
        return movieId;
    }

    public int getPage() {
        return page;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public int getTotalPages() {
        return totalPages;
    }

}
