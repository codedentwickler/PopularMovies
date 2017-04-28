package com.udacity.fasttrack.popularmovies.presentation.tablet;

import com.udacity.fasttrack.popularmovies.presentation.details.FavouriteDetailsPresenter;
import com.udacity.fasttrack.popularmovies.presentation.favourite.FavouritePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codedentwickler on 4/28/17.
 */

@Module
public class FavouriteTabletPresenterModule {

    private final FavouritePresenter mFavouritePresenter;
    private final FavouriteDetailsPresenter mDetailsPresenter;


    public FavouriteTabletPresenterModule(FavouritePresenter mFavouritePresenter, FavouriteDetailsPresenter mDetailsPresenter) {
        this.mFavouritePresenter = mFavouritePresenter;
        this.mDetailsPresenter = mDetailsPresenter;
    }

    @Provides
    public FavouritePresenter providesFavouritePresenter(){
        return mFavouritePresenter;
    }

    public FavouriteDetailsPresenter providesDetailsPresenter(){
        return mDetailsPresenter;
    }

}
