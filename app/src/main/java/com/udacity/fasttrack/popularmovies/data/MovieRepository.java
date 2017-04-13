package com.udacity.fasttrack.popularmovies.data;

import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;

import java.util.List;

import rx.Observable;

/**
 * Created by codedentwickler on 4/9/17.
 */

public interface MovieRepository {

    Observable<List<Movie>> fetchMovies(String category);
}
