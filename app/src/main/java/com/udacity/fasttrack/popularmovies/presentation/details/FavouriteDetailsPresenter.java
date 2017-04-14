package com.udacity.fasttrack.popularmovies.presentation.details;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;
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
        mDetailView.setPresenter(this);
    }

    void showMovie(Movie movie) {
        String title = movie.getTitle();

        if (Strings.isNullOrEmpty(title)) {
            mDetailView.hideTitle();
        }
        else {
            mDetailView.showTitle(title);
        }
        mDetailView.showMovieDetails(movie);
    }


    @Override
    public void subscribe() {
        showMovie(mCurrentMovie);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();

    }
}
