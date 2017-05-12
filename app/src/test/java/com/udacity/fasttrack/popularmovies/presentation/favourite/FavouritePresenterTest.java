package com.udacity.fasttrack.popularmovies.presentation.favourite;

import com.udacity.fasttrack.popularmovies.data.MovieRepository;
import com.udacity.fasttrack.popularmovies.data.local.FavouriteService;
import com.udacity.fasttrack.popularmovies.utils.schedulers.BaseSchedulerProvider;
import com.udacity.fasttrack.popularmovies.utils.schedulers.ImmediateSchedulerProvider;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * Created by codedentwickler on 4/15/17.
 */

public class FavouritePresenterTest {

    @Mock
    MovieRepository mMovieRepository;

    @Mock
    FavouriteContract.View mFavouriteView;

    FavouriteService mFavouriteService;

    BaseSchedulerProvider mSchedulerProvider;

    FavouriteContract.Presenter mPresenter;

    @Before
    public void setUpFavouritePresenter() {

        MockitoAnnotations.initMocks(this);

        mSchedulerProvider = new ImmediateSchedulerProvider();
        mPresenter = new FavouritePresenter(mSchedulerProvider, mMovieRepository, mFavouriteService, mFavouriteView);

        // The presenter won't update the view unless it's active.
        when(mFavouriteView.isActive()).thenReturn(true);
    }

}