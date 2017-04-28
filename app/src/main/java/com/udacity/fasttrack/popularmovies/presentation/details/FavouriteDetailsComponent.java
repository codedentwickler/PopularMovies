package com.udacity.fasttrack.popularmovies.presentation.details;

import com.udacity.fasttrack.popularmovies.NetworkComponent;
import com.udacity.fasttrack.popularmovies.presentation.favourite.FavouriteActivity;
import com.udacity.fasttrack.popularmovies.presentation.favourite.FavouritePresenterModule;
import com.udacity.fasttrack.popularmovies.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by codedentwickler on 4/28/17.
 */
@FragmentScoped
@Component(dependencies = NetworkComponent.class, modules = FavouritePresenterModule.class)
public interface FavouriteDetailsComponent {

    void inject(FavouriteActivity favouriteActivity);

}