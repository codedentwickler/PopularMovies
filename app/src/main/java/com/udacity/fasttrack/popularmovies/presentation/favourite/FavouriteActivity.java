package com.udacity.fasttrack.popularmovies.presentation.favourite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.presentation.tablet.MvpController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavouriteActivity extends AppCompatActivity {

    private static final String CURRENT_MOVIE_KEY = "CURRENT_MOVIE_KEY";

    @Nullable @BindView(R.id.content_frame_detail)
    public FrameLayout mDetailContainer;

    private Unbinder unbinder;
    private MvpController mvpController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        unbinder = ButterKnife.bind(this);

        Movie movie = null;
        if (savedInstanceState != null) {
            movie = savedInstanceState.getParcelable(CURRENT_MOVIE_KEY);
        }
        // Create a MvpController every time, even after rotation.
        mvpController = MvpController.createMovieView(this, movie);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(CURRENT_MOVIE_KEY, mvpController.getCurrentMovie());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
