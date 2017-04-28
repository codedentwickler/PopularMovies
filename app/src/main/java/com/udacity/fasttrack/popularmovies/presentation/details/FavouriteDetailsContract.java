package com.udacity.fasttrack.popularmovies.presentation.details;

import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.data.remote.model.Review;
import com.udacity.fasttrack.popularmovies.data.remote.model.Trailer;
import com.udacity.fasttrack.popularmovies.presentation.base.BasePresenter;
import com.udacity.fasttrack.popularmovies.presentation.base.BaseView;

import java.util.List;

/**
 * Created by codedentwickler on 4/14/17.
 */

public interface FavouriteDetailsContract {

    interface View extends BaseView<Presenter> {

        void showMovieDetails(Movie movie);

        void showReviews(List<Review> reviews);

        void showTrailers(List<Trailer> trailers);

        void openReviewInBrowser(Review review);

        void notifyOnFavouriteRemoved();

        void notifyOnFavouriteAdded();


        void playTrailer(Trailer trailer);

        void updateMovieTrailersCardVisibilty();

        void updateMovieReviewsCardVisibility();

        boolean isActive();

    }

    interface Presenter extends BasePresenter{

        void loadMovie(Movie movie);

        void loadReviews(long movieId);

        void loadTrailers(long movieId);

        void openReview(Review review);

        void openTrailer(Trailer trailer);

        void addFavourite(Movie movie);

        void removeFavourite(Movie movie);

        void onFabClicked(Movie movie);
    }
}
