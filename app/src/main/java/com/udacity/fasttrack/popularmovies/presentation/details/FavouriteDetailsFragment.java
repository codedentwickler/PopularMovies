package com.udacity.fasttrack.popularmovies.presentation.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.udacity.fasttrack.popularmovies.utils.Utils.HttpUtils.IMAGE_POSTER_BASE_URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouriteDetailsFragment extends Fragment implements FavouriteDetailsContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.poster)
    ImageView mPosterImage;
    @BindView(R.id.plot)
    TextView mPlot;
    @BindView(R.id.release_date)
    TextView mReleaseDate;
    @BindView(R.id.rating)
    TextView mRating;

    private Unbinder unbinder;

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
        View view = inflater.inflate(R.layout.favourite_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showMovieDetails(Movie movie) {

        mPlot.setText(movie.getOverview());
        mRating.setText(movie.getVoteAverage().toString());
        mTitle.setText(movie.getOriginalTitle());
        mReleaseDate.setText(movie.getReleaseDate());

        Glide.with(this.getContext())
                .load(IMAGE_POSTER_BASE_URL + movie.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .dontAnimate()
                .into(mPosterImage);
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
