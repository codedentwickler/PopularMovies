package com.udacity.fasttrack.popularmovies.presentation.favourite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.fasttrack.popularmovies.R;
import com.udacity.fasttrack.popularmovies.injection.Injection;
import com.udacity.fasttrack.popularmovies.utils.ActivityUtils;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        FavouriteFragment favouriteFragment =
                (FavouriteFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (favouriteFragment == null) {
            // Create the fragment
            favouriteFragment = FavouriteFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), favouriteFragment, R.id.container);
        }

        // Create the presenter
        new FavouritePresenter(Injection.provideSchedulerProvider(),
                Injection.provideMovieRepo(), favouriteFragment);

    }
}
