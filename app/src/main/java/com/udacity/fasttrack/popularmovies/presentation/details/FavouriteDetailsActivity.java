package com.udacity.fasttrack.popularmovies.presentation.details;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.injection.Injection;
import com.udacity.fasttrack.popularmovies.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.udacity.fasttrack.popularmovies.presentation.details.FavouriteDetailsFragment.ARGUMENT_MOVIE;
import static com.udacity.fasttrack.popularmovies.utils.Utils.HttpUtils.IMAGE_POSTER_BASE_URL;

public class FavouriteDetailsActivity extends AppCompatActivity {

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.backdrop_image)
    ImageView movieBackdropImage;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        unbinder = ButterKnife.bind(this);

        // Get the requested movie
        Movie movie = getIntent().getParcelableExtra(ARGUMENT_MOVIE);
        initToolbar(movie);

        FavouriteDetailsFragment detailsFragment =
                (FavouriteDetailsFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.content_details_container);
        if (detailsFragment == null) {
            // Create the fragment
            detailsFragment = FavouriteDetailsFragment.newInstance(                                                                                                                                                                                                                                       );
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), detailsFragment, R.id.content_details_container);
        }

        // Create the presenter
        new FavouriteDetailsPresenter(
                movie, detailsFragment,
                Injection.provideFavouriteService(this),
                Injection.provideSchedulerProvider(),
                Injection.provideMovieRepo());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initToolbar(Movie movie) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(view -> onBackPressed());
        }
        collapsingToolbarLayout.setTitle(movie.getOriginalTitle());
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        setTitle("");
        Glide.with(this)
                .load(IMAGE_POSTER_BASE_URL  + movie.getBackdropPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(movieBackdropImage);
    }

}
