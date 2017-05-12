package com.udacity.fasttrack.popularmovies.presentation.favourite;

import android.support.annotation.NonNull;
import android.util.Log;

import com.udacity.fasttrack.popularmovies.data.MovieRepository;
import com.udacity.fasttrack.popularmovies.data.local.FavouriteService;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by codedentwickler on 4/12/17.
 */

public class FavouritePresenter implements FavouriteContract.Presenter {

    private static final String TAG = FavouritePresenter.class.getSimpleName();

    @NonNull
    private final FavouriteContract.View mFavouriteView;

    private CompositeSubscription mSubscriptions;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    private final MovieRepository mMovieRepository;
    private final FavouriteService mFavouriteService;

    public FavouritePresenter(@NonNull BaseSchedulerProvider schedulerProvider,
                              @NonNull MovieRepository movieRepository,
                              @NonNull FavouriteService favouriteService,
                              @NonNull FavouriteContract.View favouriteView) {
        this.mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");
        this.mMovieRepository = checkNotNull(movieRepository,"movieRepository cannot be null");
        this.mFavouriteService = checkNotNull(favouriteService,"favouriteService cannot be null");
        this.mFavouriteView = checkNotNull(favouriteView,"favouriteView cannot be null");


        mSubscriptions = new CompositeSubscription();
        this.mFavouriteView.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadMovies(String pref) {
        loadMoviesWithPref(pref);
    }

    @Override
    public void loadFavourites() {
        mFavouriteView.setLoadingIndicator(true);
        Subscription subscription = mFavouriteService.getFavouritesAsObservables()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Subscriber<List<Movie>>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        mFavouriteView.setLoadingIndicator(false);
                        Log.e(TAG, e.getMessage());
                        mFavouriteView.showLoadingErrorMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        mFavouriteView.setLoadingIndicator(false);
                        Log.e(TAG, movies.size() + " was fetched from Favourite service");

                        mFavouriteView.showMoviesResults(movies);
                    }
                });
        mSubscriptions.add(subscription);
    }

    private void loadMoviesWithPref(String category) {

        mFavouriteView.setLoadingIndicator(true);
        Subscription subscription = mMovieRepository.fetchMovies(category)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Subscriber<List<Movie>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        mFavouriteView.setLoadingIndicator(false);
                        Log.e(TAG, e.getMessage());
                        mFavouriteView.showLoadingErrorMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        mFavouriteView.setLoadingIndicator(false);
                        Log.d(TAG, movies.size() + " was fetched from MovieDb service");

                        mFavouriteView.showMoviesResults(movies);

                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void setNetworkError() {
        mFavouriteView.showNetworkError();
    }

    @Override
    public void openMovieDetails(@NonNull Movie movie) {
        mFavouriteView.showMovieDetailsUi(movie);
    }

}
