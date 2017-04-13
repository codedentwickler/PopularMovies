package com.udacity.fasttrack.popularmovies.data;

import com.udacity.fasttrack.popularmovies.data.remote.MovieDbRestService;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;

import java.io.IOException;
import java.util.List;

import rx.Observable;

/**
 * Created by codedentwickler on 4/9/17.
 */

public class MovieRepositoryImplementation implements MovieRepository {

    private MovieDbRestService movieDbRestService;

    public MovieRepositoryImplementation(MovieDbRestService movieDbRestService) {
        this.movieDbRestService = movieDbRestService;
    }

    @Override
    public Observable<List<Movie>> fetchMovies(final String category) {

        return Observable.defer(() -> movieDbRestService.fetchAllMovies(category)
                .concatMap(moviesList -> Observable.from(moviesList.getMovies())
                        .toList()))

                .retryWhen(observable -> observable.flatMap(o -> {
                    if (o instanceof IOException) {
                        return Observable.just(null);
                    }
                    return Observable.error(o);
                }));
    }

}
