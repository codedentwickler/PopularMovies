package com.udacity.fasttrack.popularmovies.presentation.details;

import android.support.annotation.NonNull;
import android.util.Log;

import com.udacity.fasttrack.popularmovies.data.MovieRepository;
import com.udacity.fasttrack.popularmovies.data.local.FavouriteService;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.data.remote.model.Review;
import com.udacity.fasttrack.popularmovies.data.remote.model.Trailer;
import com.udacity.fasttrack.popularmovies.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by codedentwickler on 4/14/17.
 */

public class FavouriteDetailsPresenter implements FavouriteDetailsContract.Presenter {

    private static final String TAG = FavouriteDetailsFragment.class.getSimpleName();
    private Movie mCurrentMovie;

    private final FavouriteDetailsContract.View mDetailView;

    private final MovieRepository mMovieRepository;
    private final BaseSchedulerProvider mSchedulerProvider;
    private final FavouriteService mFavouriteService;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public FavouriteDetailsPresenter(Movie movie,
                                     FavouriteDetailsContract.View mDetailView,
                                     FavouriteService favouriteService,
                                     @NonNull BaseSchedulerProvider schedulerProvider,
                                     @NonNull MovieRepository movieRepository) {
        this.mCurrentMovie = movie;
        this.mDetailView = mDetailView;
        this.mFavouriteService = checkNotNull(favouriteService);
        this.mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");
        this.mMovieRepository = checkNotNull(movieRepository, "movieRepository cannot be null");

        mSubscriptions = new CompositeSubscription();
        this.mDetailView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadMovie();
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void loadMovie() {
        mDetailView.showMovieDetails(mCurrentMovie);
        updateFavouriteFabStatus();
    }

    @Override
    public void loadReviews(long movieId) {
        mDetailView.updateMovieReviewsCardVisibility();
        Subscription subscription =
                mMovieRepository.getMovieReviews(movieId)
                        .subscribeOn(mSchedulerProvider.computation())
                        .observeOn(mSchedulerProvider.ui())
                        .subscribe(new Subscriber<List<Review>>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, e.getMessage(), e);
                                mDetailView.showLoadingErrorMessage(e.getMessage());
                            }

                            @Override
                            public void onNext(List<Review> reviews) {
                                Log.d(TAG, reviews.size() + " reviews was fetched from MovieDb service");
                                mDetailView.showReviews(reviews);
                                mDetailView.updateMovieReviewsCardVisibility();
                            }
                        });
        mSubscriptions.add(subscription);

    }

    @Override
    public void loadTrailers(long movieId) {
        mDetailView.updateMovieTrailersCardVisibilty();
        Subscription subscription = mMovieRepository.getMovieTrailers(movieId)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Subscriber<List<Trailer>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDetailView.showLoadingErrorMessage(e.getMessage());
                        Log.e(TAG, e.getMessage(), e);
                    }

                    @Override
                    public void onNext(List<Trailer> trailers) {
                        Log.d(TAG, trailers.size() + " trailers was fetched from MovieDb service");
                        mDetailView.showTrailers(trailers);
                        mDetailView.updateMovieTrailersCardVisibilty();
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void setCurrentMovie(Movie movie) {
        this.mCurrentMovie = movie;
    }

    @Override
    public void openReview(Review review) {
        mDetailView.openReviewInBrowser(review);
    }

    @Override
    public void openTrailer(Trailer trailer) {
        mDetailView.playTrailer(trailer);
    }

    @Override
    public void addFavourite() {
        mFavouriteService.addToFavorites(mCurrentMovie);
        mDetailView.notifyOnFavouriteAdded();
    }

    @Override
    public void removeFavourite() {
        mFavouriteService.removeFromFavorites(mCurrentMovie);
        mDetailView.notifyOnFavouriteRemoved();
    }

    @Override
    public void toggleFavourite() {
        if (mFavouriteService.isFavorite(mCurrentMovie)) {
            removeFavourite();
        } else {
            addFavourite();
        }
    }

    private void updateFavouriteFabStatus() {

        if (mCurrentMovie != null) {
            if (mFavouriteService.isFavorite(mCurrentMovie)) {
                mDetailView.activateFab();
            } else {
                mDetailView.deactivateFab();
            }
        }
    }
}
