package com.udacity.fasttrack.popularmovies.presentation.details;

import android.support.annotation.NonNull;

import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;

import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by codedentwickler on 4/14/17.
 */

public class FavouriteDetailsPresenter implements FavouriteDetailsContract.Presenter {

    private final Movie mCurrentMovie;

    private final FavouriteDetailsContract.View mDetailView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public FavouriteDetailsPresenter(Movie movie, FavouriteDetailsContract.View mDetailView) {
        this.mCurrentMovie = checkNotNull(movie);
        this.mDetailView = mDetailView;

        mSubscriptions = new CompositeSubscription();
        this.mDetailView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mDetailView.showMovieDetails(mCurrentMovie);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();

    }
}
