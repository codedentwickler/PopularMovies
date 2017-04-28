package com.udacity.fasttrack.popularmovies.presentation.favourite;

import com.udacity.fasttrack.popularmovies.utils.FragmentScoped;

import dagger.Component;

/**
 * Created by codedentwickler on 4/28/17.
 */
@FragmentScoped
@Component(dependencies = NetworkComponent.class, modules = FavouritePresenterModule.class)
public interface FavouriteComponent {

    void inject(FavouriteActivity favouriteActivity);

}