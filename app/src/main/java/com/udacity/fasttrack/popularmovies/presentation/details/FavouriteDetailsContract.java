package com.udacity.fasttrack.popularmovies.presentation.details;

import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.presentation.base.BasePresenter;
import com.udacity.fasttrack.popularmovies.presentation.base.BaseView;

/**
 * Created by codedentwickler on 4/14/17.
 */

public interface FavouriteDetailsContract {

    interface View extends BaseView<Presenter> {

        void showMovieDetails(Movie movie);

        boolean isActive();

    }

    interface Presenter extends BasePresenter{


    }

}
