package com.udacity.fasttrack.popularmovies.data;

import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.data.remote.model.Review;
import com.udacity.fasttrack.popularmovies.data.remote.model.Trailer;

import java.util.List;

import rx.Observable;

/**
 * Created by codedentwickler on 4/9/17.
 */

public interface MovieRepository {

    Observable<List<Movie>> fetchMovies(String category);

    Observable<Movie> fetchMovieWithId(long movieId);

    Observable<List<Movie>> fetchMoviesWithId();

    Observable<List<Trailer>> getMovieTrailers(long movieId);

    Observable<List<Review>> getMovieReviews(long movieId);

}
