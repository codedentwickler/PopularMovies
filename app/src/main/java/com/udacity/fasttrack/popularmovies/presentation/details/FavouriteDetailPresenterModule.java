package com.udacity.fasttrack.popularmovies.presentation.details;

import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codedentwickler on 4/28/17.
 */

@Module
public class FavouriteDetailPresenterModule {

    private final FavouriteDetailsContract.View mView;

    private final Movie mMovie;

    public FavouriteDetailPresenterModule(FavouriteDetailsContract.View mView, Movie mMovie) {
        this.mView = mView;
        this.mMovie = mMovie;
    }

    @Provides
    FavouriteDetailsContract.View provideFavouriteDetailContractView() {
        return mView;
    }

    @Provides
    Movie provideMovie() {
        return mMovie;
    }
}
