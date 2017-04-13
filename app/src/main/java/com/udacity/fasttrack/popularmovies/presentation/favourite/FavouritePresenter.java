package com.udacity.fasttrack.popularmovies.presentation.favourite;

import android.support.annotation.NonNull;

import com.udacity.fasttrack.popularmovies.data.MovieRepository;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by codedentwickler on 4/12/17.
 */

class FavouritePresenter implements FavouriteContract.Presenter {

    private boolean mFirstLoad = true;

    @NonNull
    private final FavouriteContract.View mFavouriteView;

    private CompositeSubscription mSubscriptions;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    private final MovieRepository mMovieRepository;

    public FavouritePresenter(@NonNull BaseSchedulerProvider mSchedulerProvider,
                              @NonNull MovieRepository mMovieRepository,
                              @NonNull FavouriteContract.View mFavouriteView) {
        this.mSchedulerProvider = checkNotNull(mSchedulerProvider, "schedulerProvider cannot be null");
        this.mMovieRepository = checkNotNull(mMovieRepository,"movieRepository cannot be null");
        this.mFavouriteView = checkNotNull(mFavouriteView,"favouriteView cannot be null");

        mSubscriptions = new CompositeSubscription();
        this.mFavouriteView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    public void loadMoviesWithPref(String pref) {
        mFavouriteView.setLoadingIndicator(true);
        Subscription subscription = mMovieRepository.fetchMovies(pref)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Subscriber<List<Movie>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        mFavouriteView.setLoadingIndicator(false);
                        mFavouriteView.showLoadingErrorMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        mFavouriteView.setLoadingIndicator(false);
                        mFavouriteView.showMoviesResults(movies);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void openMovieDetails(@NonNull Movie movie) {

    }

}
