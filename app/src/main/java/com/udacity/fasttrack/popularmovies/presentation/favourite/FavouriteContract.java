package com.udacity.fasttrack.popularmovies.presentation.favourite;

import android.support.annotation.NonNull;

import com.udacity.fasttrack.popularmovies.presentation.base.BasePresenter;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.presentation.base.BaseView;

import java.util.List;

/**
 * Created by codedentwickler on 4/12/17.
 */

public interface FavouriteContract {

    interface View extends BaseView<Presenter> {

        void showMoviesResults(List<Movie> movies);

        void showLoadingErrorMessage(String message);

        void setLoadingIndicator(boolean active);

        void showMovieDetailsUi(Movie movie);

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void loadMoviesWithPref(String pref);

        void openMovieDetails(@NonNull Movie movie);

    }
}
