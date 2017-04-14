package com.udacity.fasttrack.popularmovies.presentation.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouriteDetailsFragment extends Fragment implements FavouriteDetailsContract.View {

    public static final String ARGUMENT_MOVIE = "movie";
    private FavouriteDetailsContract.Presenter mPresenter;

    public FavouriteDetailsFragment() {
    }

    public static FavouriteDetailsFragment newInstance(Movie movie) {

        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_MOVIE, movie);
        FavouriteDetailsFragment fragment = new FavouriteDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite_details, container, false);


        return view;
    }

    @Override
    public void hideTitle() {

    }

    @Override
    public void showTitle(String title) {

    }

    @Override
    public void showMovieDetails(Movie movie) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(FavouriteDetailsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
