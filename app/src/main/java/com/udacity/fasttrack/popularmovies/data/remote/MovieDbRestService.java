package com.udacity.fasttrack.popularmovies.data.remote;

import com.udacity.fasttrack.popularmovies.BuildConfig;
import com.udacity.fasttrack.popularmovies.data.remote.model.MoviesList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by codedentwickler on 4/9/17.
 */

public interface MovieDbRestService {


    @GET("movie/{category}?api_key=" + BuildConfig.MOVIE_API_KEY)
    Observable<MoviesList> fetchAllMovies(@Path("category") String category);

}