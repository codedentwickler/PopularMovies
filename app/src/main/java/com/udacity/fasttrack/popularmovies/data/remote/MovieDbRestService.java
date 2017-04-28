package com.udacity.fasttrack.popularmovies.data.remote;

import com.udacity.fasttrack.popularmovies.BuildConfig;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.data.remote.model.MoviesList;
import com.udacity.fasttrack.popularmovies.data.remote.model.ReviewsList;
import com.udacity.fasttrack.popularmovies.data.remote.model.TrailersList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by codedentwickler on 4/9/17.
 */

public interface MovieDbRestService {

    @GET("movie/{category}?api_key=" + BuildConfig.MOVIE_API_KEY)
    Observable<MoviesList> fetchAllMovies(@Path("category") String category);


    @GET("movie/{id}?api_key=" + BuildConfig.MOVIE_API_KEY)
    Observable<Movie> fetchMovieWithId(@Path("id") long movieId);


    @GET("movie/{id}/videos?api_key=" + BuildConfig.MOVIE_API_KEY)
    Observable<TrailersList> getMovieTrailers(@Path("id") long movieId);

    @GET("movie/{id}/reviews?api_key=" + BuildConfig.MOVIE_API_KEY)
    Observable<ReviewsList> getMovieReviews(@Path("id") long movieId);

}