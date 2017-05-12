package com.udacity.fasttrack.popularmovies.presentation.favourite;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import android.widget.TextView;

import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.presentation.details.FavouriteDetailsActivity;
import com.udacity.fasttrack.popularmovies.utils.ActivityUtils;
import com.udacity.fasttrack.popularmovies.utils.NetworkUtils;
import com.udacity.fasttrack.popularmovies.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.udacity.fasttrack.popularmovies.presentation.details.FavouriteDetailsFragment.ARGUMENT_MOVIE;
import static com.udacity.fasttrack.popularmovies.utils.Utils.showMessage;

/**
 * A simple {@link Fragment} subclass.
 */

public class FavouriteFragment extends Fragment implements FavouriteContract.View{

    @BindView(R.id.recycler_view)
    RecyclerView mMovieRecyclerView;

    @BindView(R.id.root_view)
    ConstraintLayout mRootView;

    @BindView(R.id.noMovies_text_text)
    TextView noMoviesTextView;

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

                });
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
            if (category.equals(getString(R.string.pref_favourite))){
                mPresenter.loadFavourites();
            } else {
                mPresenter.loadMovies(category);
            }
        } else {
            mPresenter.loadFavourites();
            mPresenter.setNetworkError();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpMovieRecycler();
        loadMovies();
    }

    void setUpMovieRecycler(){
        GridLayoutManager layoutManager = new GridLayoutManager(
                getActivity(),calculateNoOfColumns());
        mMovieRecyclerView.setLayoutManager(layoutManager);

        mMovieRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMovieRecyclerView.setAdapter(mFavouriteAdapter);
    }

    public int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if (ActivityUtils.isTablet(getActivity())){
            dpWidth = dpWidth / 3;
        }
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
                editor.putString(getString(R.string.category_key), getString(R.string.pref_most_popular));
                actionBar.setTitle(R.string.popular);
                // Commit the edits!
                editor.apply();
                loadMovies();
                break;

            case R.id.top_rated:
                editor.putString(getString(R.string.category_key), getString(R.string.pref_top_rated));
                actionBar.setTitle(R.string.top_rated);
                // Commit the edits!
                editor.apply();
                loadMovies();
                break;

            case R.id.favourite:
                editor.putString(getString(R.string.category_key),
                        getString(R.string.pref_favourite));
                actionBar.setTitle(R.string.favorites_grid_title);
                // Commit the edits!
                editor.apply();
                loadMovies();
        }

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
        showMessage(mRootView, getString(R.string.error_loading_movies));
    }

    @Override
    public void showNetworkError() {
        noMoviesTextView.setText(R.string.network_error);
        noMoviesTextView.setVisibility(View.VISIBLE);
        Utils.showNetworkError(mRootView, v -> loadMovies());
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) showLoading();
        else hideLoading();

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

    private void showLoading() {
        mMovieRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        noMoviesTextView.setVisibility(View.GONE);
    }

    private void hideLoading() {
        mMovieRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        noMoviesTextView.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (mFavouriteAdapter.getItemCount() == 0 && sharedPreferences.getString
                (getString(R.string.category_key), getString(R.string.pref_most_popular))
                .equals(getString(R.string.pref_favourite))) {
            noMoviesTextView.setText(R.string.no_favorite_movies);
            noMoviesTextView.setVisibility(View.GONE);
        }
    }

}
