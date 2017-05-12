package com.udacity.fasttrack.popularmovies.presentation.tablet;

import android.support.annotation.NonNull;

import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.data.remote.model.Review;
import com.udacity.fasttrack.popularmovies.data.remote.model.Trailer;
import com.udacity.fasttrack.popularmovies.presentation.details.FavouriteDetailsContract;
import com.udacity.fasttrack.popularmovies.presentation.details.FavouriteDetailsPresenter;
import com.udacity.fasttrack.popularmovies.presentation.favourite.FavouriteContract;
import com.udacity.fasttrack.popularmovies.presentation.favourite.FavouritePresenter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by codedentwickler on 4/21/17.
 */

public class FavouriteTabletPresenter implements FavouriteContract.Presenter
        , FavouriteDetailsContract.Presenter {

    private FavouritePresenter mFavouritePresenter;
    private FavouriteDetailsPresenter mDetailsPresenter;

    FavouriteTabletPresenter(FavouritePresenter favouritePresenter) {
        this.mFavouritePresenter = checkNotNull(favouritePresenter);
    }

    public FavouriteDetailsPresenter getDetailsPresenter(){
        return mDetailsPresenter;
    }

    void setDetailsPresenter(FavouriteDetailsPresenter detailsPresenter){
        mDetailsPresenter = detailsPresenter;
    }

    @Override
    public void subscribe() {
        mFavouritePresenter.subscribe();
        if (mDetailsPresenter != null)
            mDetailsPresenter.subscribe();
    }

    @Override
    public void unSubscribe() {
        mFavouritePresenter.unSubscribe();
        if (mDetailsPresenter != null)
            mDetailsPresenter.unSubscribe();
    }

    @Override
    public void loadMovies(String pref) {
        mFavouritePresenter.loadMovies(pref);
    }

    @Override
    public void loadFavourites() {
        mFavouritePresenter.loadFavourites();
    }

    @Override
    public void setNetworkError() {
        mFavouritePresenter.setNetworkError();
    }

    @Override
    public void openMovieDetails(@NonNull Movie movie) {
        mDetailsPresenter.setCurrentMovie(movie);
        mDetailsPresenter.loadMovie();
    }

    @Override
    public void loadMovie() {
        mDetailsPresenter.loadMovie();
    }

    @Override
    public void loadReviews(long movieId) {
        mDetailsPresenter.loadReviews(movieId);
    }

    @Override
    public void loadTrailers(long movieId) {
        mDetailsPresenter.loadTrailers(movieId);
    }

    @Override
    public void setCurrentMovie(Movie movie) {
        mDetailsPresenter.setCurrentMovie(movie);
    }

    @Override
    public void openReview(Review review) {
        mDetailsPresenter.openReview(review);
    }

    @Override
    public void openTrailer(Trailer trailer) {
        mDetailsPresenter.openTrailer(trailer);
    }

    @Override
    public void addFavourite() {
        mDetailsPresenter.addFavourite();
    }

    @Override
    public void removeFavourite() {
        mDetailsPresenter.removeFavourite();
    }

    @Override
    public void toggleFavourite() {
        mDetailsPresenter.toggleFavourite();
    }

}
