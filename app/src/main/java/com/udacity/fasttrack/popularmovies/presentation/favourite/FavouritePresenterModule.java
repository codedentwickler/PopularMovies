package com.udacity.fasttrack.popularmovies.presentation.favourite;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codedentwickler on 4/28/17.
 */

@Module
public class FavouritePresenterModule {

    private final FavouriteContract.View mView;


    public FavouritePresenterModule(FavouriteContract.View mView) {
        this.mView = mView;
    }

    @Provides
    FavouriteContract.View providesFavouriteContractView(){
        return mView;
    }
}
