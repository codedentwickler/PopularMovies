package com.udacity.fasttrack.popularmovies;

import android.app.Application;

/**
 * Created by codedentwickler on 4/28/17.
 */

public class MoviesApplication extends Application {

    private FavouriteServiceComponent mFavouriteServiceComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mFavouriteServiceComponent = DaggerFavouriteServiceComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();
    }

    public FavouriteServiceComponent provideFavouriteServiceComponent(){
        return mFavouriteServiceComponent;
    }
}
