package com.udacity.fasttrack.popularmovies.presentation.favourite;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */

public class FavouriteFragment extends Fragment implements FavouriteContract.View{

    @BindView(R.id.recycler_view)
    private RecyclerView mMovieRecyclerView;

    @BindView(R.id.toolbar)
    private Toolbar mToolbar;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;


    private List<Movie> mMovies;
    private FavouriteAdapter mFavouriteAdapter;
    private FavouriteContract.Presenter mPresenter;
    private String mCategory;


    public FavouriteFragment() {
        // Required empty public constructor
    }

    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavouriteAdapter = new FavouriteAdapter(new ArrayList<>(0),
                movie -> {
                    // Item Click Listener here

                }, this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this,view);

        setHasOptionsMenu(true);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mCategory = sharedPreferences.getString(getString(R.string.category_key),
                getString(R.string.pref_default));
        mMovieRecyclerView.setAdapter(mFavouriteAdapter);

        mPresenter.loadMoviesWithPref(mCategory);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mMovieRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(FavouriteContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void showMoviesResults(List<Movie> movies) {

    }

    @Override
    public void showLoadingErrorMessage(String message) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showMovieDetailsUi(Movie movie) {

    }

    @Override
    public boolean isActive() {
        return false;
    }


}
