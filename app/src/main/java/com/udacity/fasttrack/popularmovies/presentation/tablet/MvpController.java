package com.udacity.fasttrack.popularmovies.presentation.tablet;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.injection.Injection;
import com.udacity.fasttrack.popularmovies.presentation.details.FavouriteDetailsFragment;
import com.udacity.fasttrack.popularmovies.presentation.details.FavouriteDetailsPresenter;
import com.udacity.fasttrack.popularmovies.presentation.favourite.FavouriteFragment;
import com.udacity.fasttrack.popularmovies.presentation.favourite.FavouritePresenter;
import com.udacity.fasttrack.popularmovies.utils.ActivityUtils;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.udacity.fasttrack.popularmovies.utils.ActivityUtils.isTablet;

/**
 * Created by codedentwickler on 4/21/17.
 */

public class MvpController {

    private final FragmentActivity mFragmentActivity;

    private FavouriteTabletPresenter mTabletPresenter;

    private FavouritePresenter mFavouritePresenter;

    @Nullable
    private final Movie mCurrentMovie;

    // Force factory method, prevent direct instantiation:
    private MvpController(@NonNull FragmentActivity fragmentActivity,
                          @Nullable Movie movie) {
        this.mFragmentActivity = fragmentActivity;
        this.mCurrentMovie = movie;
    }

    /**
     * Creates a controller for a movie view for phones or tablets.
     * @param fragmentActivity the context activity
     * @return a TasksMvpController
     */
    public static MvpController createMovieView(
            @NonNull FragmentActivity fragmentActivity, @Nullable Movie movie) {

        checkNotNull(fragmentActivity);
        MvpController mvpController= new MvpController(fragmentActivity, movie);

        mvpController.initView();
        return mvpController;
    }

    private void initView() {
        if (isTablet(mFragmentActivity)) {
            createTabletElements();
        } else {
            createPhoneElements();
        }
    }

    private void createPhoneElements() {
        FavouriteFragment favouriteFragment = findOrCreateFavouriteFragment(R.id.content_frame_list);
        mFavouritePresenter = createListPresenter(favouriteFragment);
        favouriteFragment.setPresenter(mFavouritePresenter);
    }

    private void createTabletElements() {
        // Fragment 1: List
        FavouriteFragment favouriteFragment = findOrCreateFavouriteFragment(R.id.content_frame_list);
        mFavouritePresenter = createListPresenter(favouriteFragment);

        // Fragment 2: Detail
        FavouriteDetailsFragment detailsFragment = findOrCreateDetailFragmentForTablet();
        FavouriteDetailsPresenter detailsPresenter = createDetailPresenter(detailsFragment);

        // Fragments connect to their presenters through a tablet presenter:
        mTabletPresenter = new FavouriteTabletPresenter(mFavouritePresenter);

        favouriteFragment.setPresenter(mTabletPresenter);
        detailsFragment.setPresenter(mTabletPresenter);
        mTabletPresenter.setDetailsPresenter(detailsPresenter);
    }

    @NonNull
    private FavouriteDetailsPresenter createDetailPresenter(FavouriteDetailsFragment detailsFragment){
        return new FavouriteDetailsPresenter(
                mCurrentMovie, detailsFragment,
                Injection.provideFavouriteService(mFragmentActivity),
                Injection.provideSchedulerProvider(),
                Injection.provideMovieRepo(mFragmentActivity));
    }

    private FavouritePresenter createListPresenter(FavouriteFragment favouriteFragment) {
        mFavouritePresenter = new FavouritePresenter(Injection.provideSchedulerProvider(),
                Injection.provideMovieRepo(mFragmentActivity),
                Injection.provideFavouriteService(mFragmentActivity),
                favouriteFragment);

        return mFavouritePresenter;
    }

    @NonNull
    private FavouriteFragment findOrCreateFavouriteFragment(@IdRes int fragmentId) {
        FavouriteFragment favouriteFragment = (FavouriteFragment) getFragmentById(fragmentId);
        if (favouriteFragment == null) {
            // Create the fragment
            favouriteFragment = FavouriteFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), favouriteFragment, fragmentId);
        }
        return favouriteFragment;
    }


    @NonNull
    private FavouriteDetailsFragment findOrCreateDetailFragmentForTablet() {
        FavouriteDetailsFragment detailsFragment =
                (FavouriteDetailsFragment) getFragmentById(R.id.content_frame_detail);
        if (detailsFragment == null) {
            // Create the fragment
            detailsFragment = FavouriteDetailsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), detailsFragment, R.id.content_frame_detail);
        }
        return detailsFragment;
    }

    private Fragment getFragmentById(int contentFrame_detail) {
        return getSupportFragmentManager().findFragmentById(contentFrame_detail);
    }


    public Movie getCurrentMovie() {
        return mCurrentMovie;
    }

    private FragmentManager getSupportFragmentManager() {
        return mFragmentActivity.getSupportFragmentManager();
    }

}
