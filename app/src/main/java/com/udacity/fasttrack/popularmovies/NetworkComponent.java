package com.udacity.fasttrack.popularmovies;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by codedentwickler on 4/28/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface NetworkComponent {

    void inject();

    void inject();

    void inject();

}
