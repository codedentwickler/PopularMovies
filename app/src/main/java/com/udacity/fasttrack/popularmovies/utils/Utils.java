package com.udacity.fasttrack.popularmovies.utils;

/**
 * Created by codedentwickler on 4/12/17.
 */

public class Utils {

    public static final class HttpUtils {
        public static final String BASE_URL = "http://api.themoviedb.org/3/";
        public static final String IMAGE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500";
    }

    public static final class REFERENCE {
        public static final String MOVIE = Config.PACKAGE_NAME + "movie";
    }

    public static final class Config {
        public static final String PACKAGE_NAME ="com.udacity.fasttrack.popularmovies.";
    }
}
