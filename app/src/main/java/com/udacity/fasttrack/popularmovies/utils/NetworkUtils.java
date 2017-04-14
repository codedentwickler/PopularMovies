package com.udacity.fasttrack.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by codedentwickler on 4/14/17.
 */

public class NetworkUtils {

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null;
    }

}
