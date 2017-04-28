package com.udacity.fasttrack.popularmovies;

import com.udacity.fasttrack.popularmovies.data.local.FavouriteService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by codedentwickler on 4/28/17.
 */

@Singleton
@Component( modules = {ApplicationModule.class})
public interface FavouriteServiceComponent {

    FavouriteService provideFavouriteService();

}
