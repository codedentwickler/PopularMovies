package com.udacity.fasttrack.popularmovies.presentation.favourite;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.presentation.details.FavouriteDetailsActivity;
import com.udacity.fasttrack.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.udacity.fasttrack.popularmovies.presentation.details.FavouriteDetailsFragment.ARGUMENT_MOVIE;

/**
 * A simple {@link Fragment} subclass.
 */

public class FavouriteFragment extends Fragment implements FavouriteContract.View{

    @BindView(R.id.recycler_view)
    RecyclerView mMovieRecyclerView;

    @BindView(R.id.root_view)
    ConstraintLayout mRootView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private Unbinder unbinder;

    private FavouriteAdapter mFavouriteAdapter;
    private FavouriteContract.Presenter mPresenter;

    private final int GRID_COLUMN_WIDTH = 190 ;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mFavouriteAdapter = new FavouriteAdapter(new ArrayList<>(0),
                movie -> {
                    // Item Click Listener is here
                    mPresenter.openMovieDetails(movie);

                }, this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        unbinder = ButterKnife.bind(this,view);

        return view;
    }

    void loadMovies(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String category = sharedPreferences.getString(getString(R.string.category_key),
                getString(R.string.pref_most_popular));

        if (NetworkUtils.isNetworkAvailable(this.getContext())) {
            mPresenter.loadMovies(category);
        } else {
            mPresenter.setNetworkError();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridLayoutManager layoutManager = new GridLayoutManager(
                getActivity(),calculateNoOfColumns());
        mMovieRecyclerView.setLayoutManager(layoutManager);

        mMovieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMovieRecyclerView.setAdapter(mFavouriteAdapter);
        loadMovies();
    }

    public int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / GRID_COLUMN_WIDTH);
        if (noOfColumns < 2) return 2;
        return noOfColumns;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.favourite_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ActionBar actionBar  = ((AppCompatActivity)getActivity()).getSupportActionBar();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (item.getItemId()) {

            case R.id.most_popular:
                item.setCheckable(true);
                editor.putString(getString(R.string.category_key), getString(R.string.pref_most_popular));
                actionBar.setTitle(R.string.popular);
                break;

            case R.id.top_rated:
                item.setCheckable(true);
                editor.putString(getString(R.string.category_key), getString(R.string.pref_top_rated));
                actionBar.setTitle(R.string.top_rated);
                break;
        }

        // Commit the edits!
        editor.apply();
        loadMovies();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void setPresenter(FavouriteContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showMoviesResults(List<Movie> movies) {

        mFavouriteAdapter.replaceData(movies);
        mMovieRecyclerView.setVisibility(View.VISIBLE);
     }

    @Override
    public void showLoadingErrorMessage(String message) {
        showMessage(getString(R.string.error_loading_movies));
    }

    @Override
    public void showNetworkError() {
        showMessage(getString(R.string.network_error_text));
    }

    private void showMessage(String message) {
        Snackbar.make(mRootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) showLoading();
        else hideLoading();

        // Make sure setRefreshing() is called after the layout is done with everything else.
//        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(active));
    }

    @Override
    public void showMovieDetailsUi(Movie movie) {
        Intent intent = new Intent(this.getContext(), FavouriteDetailsActivity.class);
        intent.putExtra(ARGUMENT_MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    public void showLoading() {
        mMovieRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        mMovieRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

}
