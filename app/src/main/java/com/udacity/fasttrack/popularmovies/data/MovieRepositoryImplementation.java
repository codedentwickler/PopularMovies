package com.udacity.fasttrack.popularmovies.data;

import com.udacity.fasttrack.popularmovies.data.remote.MovieDbRestService;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.data.remote.model.Review;
import com.udacity.fasttrack.popularmovies.data.remote.model.Trailer;

import java.io.IOException;
import java.util.List;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by codedentwickler on 4/9/17.
 */

public class MovieRepositoryImplementation implements MovieRepository {

    private static final String TAG = MovieRepositoryImplementation.class.getSimpleName();
    private MovieDbRestService movieDbRestService;

    public MovieRepositoryImplementation(MovieDbRestService movieDbRestService) {
        this.movieDbRestService = checkNotNull(movieDbRestService);
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


//    @Override
//    public Observable<Movie> fetchMovieWithId(long id) {
//
//        Log.e(TAG, "fetchMoviesWithId at the beginning");
//
//        return Observable.defer(()  -> movieDbRestService.fetchMovieWithId(id))
//
//                .retryWhen(observable -> observable.flatMap(o -> {
//                    if (o instanceof IOException) {
//                        Log.e(TAG, o.getMessage());
//                        Log.e(TAG, "fetchMoviesWithId retried");
//
//                        return Observable.just(null);
//                    }
//                    return Observable.error(o);
//                }));
//
//    }

    @Override
    public Observable<List<Trailer>> getMovieTrailers(long movieId) {
        return Observable.defer(() -> movieDbRestService.getMovieTrailers(movieId))
                .concatMap(trailersList -> Observable.from(trailersList.getTrailers())
                        .toList())

                .retryWhen(observable -> observable.flatMap(o -> {
                    if (o instanceof IOException) {
                        return Observable.just(null);
                    }
                    return Observable.error(o);
                }));
    }

    @Override
    public Observable<List<Review>> getMovieReviews(long movieId) {
        return Observable.defer(() -> movieDbRestService.getMovieReviews(movieId))
                .concatMap(reviewsList -> Observable.from(reviewsList.getReviews())
                        .toList())

                .retryWhen(observable -> observable.flatMap(o -> {
                    if (o instanceof IOException) {
                        return Observable.just(null);
                    }
                    return Observable.error(o);
                }));
    }

}
