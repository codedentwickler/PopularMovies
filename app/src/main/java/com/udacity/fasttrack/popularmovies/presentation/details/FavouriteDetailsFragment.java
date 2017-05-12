package com.udacity.fasttrack.popularmovies.presentation.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.data.remote.model.Review;
import com.udacity.fasttrack.popularmovies.data.remote.model.Trailer;
import com.udacity.fasttrack.popularmovies.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.udacity.fasttrack.popularmovies.utils.Utils.HttpUtils.IMAGE_POSTER_BASE_URL;
import static com.udacity.fasttrack.popularmovies.utils.Utils.HttpUtils.WATCH_ON_YOUTUBE_URL;
import static com.udacity.fasttrack.popularmovies.utils.Utils.showMessage;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouriteDetailsFragment extends Fragment implements FavouriteDetailsContract.View {

    public static final String ARGUMENT_MOVIE = "movie";
    private static final String TAG = FavouriteDetailsFragment.class.getSimpleName();

    private static final String MOVIE_REVIEWS_KEY = "movie_reviews_key";
    private static final String MOVIE_TRAILERS_KEY = "movie_trailers_key";

    @BindView(R.id.image_movie_detail_poster)
    ImageView movieImagePoster;
    @BindView(R.id.text_movie_original_title)
    TextView movieOriginalTitle;
    @BindView(R.id.text_movie_user_rating)
    TextView movieUserRating;
    @BindView(R.id.text_movie_release_date)
    TextView movieReleaseDate;
    @BindView(R.id.text_movie_overview)
    TextView movieOverview;
    @BindView(R.id.card_movie_detail)
    CardView cardMovieDetail;
    @BindView(R.id.card_movie_overview)
    CardView cardMovieOverview;
    @BindView(R.id.root_view)
    RelativeLayout mRootLayout;
    @Nullable
    @BindView(R.id.noMovies_text_view)
    TextView mEmptyTextView;

    @BindView(R.id.card_movie_videos)
    CardView cardMovieTrailers;
    @BindView(R.id.movie_videos)
    RecyclerView mTrailersRecyclerView;

    @BindView(R.id.card_movie_reviews)
    CardView cardMovieReviews;
    @BindView(R.id.movie_reviews)
    RecyclerView mReviewsRecyclerView;

    private FloatingActionButton mFavouriteFab;

    private Unbinder unbinder;

    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;

    private FavouriteDetailsContract.Presenter mPresenter;

    public FavouriteDetailsFragment() {
    }

    public static FavouriteDetailsFragment newInstance() {
        return new FavouriteDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTrailerAdapter = new TrailerAdapter(new ArrayList<>(0),
                trailer -> {
                    // Trailer Click Listener is here
                    mPresenter.openTrailer(trailer);
                });

        mReviewAdapter = new ReviewAdapter(new ArrayList<>(0),
                review -> {
                    // Review Click Listener is here
                    mPresenter.openReview(review);
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        setupCardsElevation();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpTrailersRecycler();
        setUpReviewsRecycler();

        mFavouriteFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if (mFavouriteFab != null) {
            mFavouriteFab.setOnClickListener(v -> onFabClicked());

        }
    }

    private void onFabClicked() {
        mPresenter.toggleFavourite();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mTrailerAdapter.getItemCount() != 0) {
            outState.putParcelableArrayList(MOVIE_TRAILERS_KEY,
                    (ArrayList<Trailer>) mTrailerAdapter.getTrailers());
        }
        if (mReviewAdapter.getItemCount() != 0) {
            outState.putParcelableArrayList(MOVIE_REVIEWS_KEY,
                    (ArrayList<Review>) mReviewAdapter.getReviews());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && isActive()) {
            mTrailerAdapter.replaceData(savedInstanceState.getParcelableArrayList(MOVIE_TRAILERS_KEY));
            mReviewAdapter.replaceData(savedInstanceState.getParcelableArrayList(MOVIE_REVIEWS_KEY));
        }
    }

    private void setUpReviewsRecycler() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mReviewsRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mReviewsRecyclerView.getContext(), layoutManager.getOrientation());
        mReviewsRecyclerView.addItemDecoration(dividerItemDecoration);

        mReviewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mReviewsRecyclerView.setAdapter(mReviewAdapter);
    }

    private void setUpTrailersRecycler() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mTrailersRecyclerView.setLayoutManager(layoutManager);

        mTrailersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTrailersRecyclerView.setAdapter(mTrailerAdapter);
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
    public void updateMovieTrailersCardVisibilty() {
        if (mTrailerAdapter == null || mTrailerAdapter.getItemCount() == 0) {
            cardMovieTrailers.setVisibility(GONE);
        } else {
            cardMovieTrailers.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateMovieReviewsCardVisibility() {
        if (mReviewAdapter == null || mReviewAdapter.getItemCount() == 0) {
            cardMovieReviews.setVisibility(GONE);
        } else {
            cardMovieReviews.setVisibility(View.VISIBLE);
        }
    }

    private void setupCardsElevation() {
        elevateCard(cardMovieDetail);
        elevateCard(cardMovieTrailers);
        elevateCard(cardMovieOverview);
        elevateCard(cardMovieReviews);
    }


    private void elevateCard(View view) {
        ViewCompat.setElevation(view,
                convertDpToPixel(getResources().getInteger(R.integer.movie_detail_content_elevation_in_dp)));
    }

    public float convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showMovieDetails(Movie movie) {

        if (movie != null) {
            mRootLayout.setVisibility(View.VISIBLE);
            mFavouriteFab.setVisibility(View.VISIBLE);
            movieOverview.setText(movie.getOverview());
            movieUserRating.setText(String.valueOf(movie.getVoteAverage()));
            movieOriginalTitle.setText(movie.getOriginalTitle());
            movieReleaseDate.setText(movie.getReleaseDate());

            Glide.with(this.getContext())
                    .load(IMAGE_POSTER_BASE_URL + movie.getPosterPath())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .dontAnimate()
                    .into(movieImagePoster);
            mPresenter.loadReviews(movie.getId());
            mPresenter.loadTrailers(movie.getId());
        } else {
            mEmptyTextView.setVisibility(View.VISIBLE);
            mFavouriteFab.setVisibility(GONE);
            mRootLayout.setVisibility(GONE);
        }
    }

    @Override
    public void showReviews(List<Review> reviews) {
        mReviewAdapter.replaceData(reviews);
        mReviewsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTrailers(List<Trailer> trailers) {
        mTrailerAdapter.replaceData(trailers);
        mTrailersRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void openReviewInBrowser(Review review) {
        if (review != null && review.getReviewUrl() != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getReviewUrl()));
            startActivity(intent);
        }
    }

    @Override
    public void notifyOnFavouriteRemoved() {
        deactivateFab();
        showMessage(mRootLayout, getString(R.string.message_removed_from_favorites),
                v -> mPresenter.addFavourite());
    }

    @Override
    public void notifyOnFavouriteAdded() {
        activateFab();
        showMessage(mRootLayout, getString(R.string.message_added_to_favorites),
                v -> mPresenter.removeFavourite());
    }

    @Override
    public void activateFab() {
        mFavouriteFab.setImageResource(R.drawable.ic_favorite_white);
    }

    @Override
    public void deactivateFab() {
        mFavouriteFab.setImageResource(R.drawable.ic_favorite_white_border);
    }

    @Override
    public void playTrailer(Trailer trailer) {
        if (trailer != null && trailer.isYoutubeVideo()) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(WATCH_ON_YOUTUBE_URL + trailer.getKey()));
            startActivity(intent);
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(FavouriteDetailsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showLoadingErrorMessage(String message) {
        showMessage(mRootLayout, getString(R.string.error_loading_movies));
    }

    @Override
    public void showNetworkError() {
        Utils.showNetworkError(mRootLayout, v -> mPresenter.loadMovie());
    }
}
