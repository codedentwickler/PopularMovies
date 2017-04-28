package com.udacity.fasttrack.popularmovies.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.udacity.fasttrack.popularmovies.R;

/**
 * Created by codedentwickler on 4/12/17.
 */

public class Utils {

    public static final class HttpUtils {
        public static final String BASE_URL = "https://api.themoviedb.org/3/";
        public static final String IMAGE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500";
        public static final String WATCH_ON_YOUTUBE_URL = "http://www.youtube.com/watch?v=";
    }

    public static void showMessage(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showMessage(View view, String message, View.OnClickListener listener) {

        Context context = view.getContext();
        Snackbar snackbar = Snackbar.make(view, message
                , Snackbar.LENGTH_LONG)
                .setAction(R.string.undo,listener)
                .setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(
               context.getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }

    public static void showNetworkError(View view, View.OnClickListener listener) {
        Context context = view.getContext();
        Snackbar snackbar = Snackbar.make(view, context.getString(R.string.network_error_text)
                , Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry,listener)
                .setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(
                context.getResources().getColor(R.color.colorAccent));
        snackbar.show();

    }
}
