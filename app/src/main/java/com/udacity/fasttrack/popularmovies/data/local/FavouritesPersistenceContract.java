package com.udacity.fasttrack.popularmovies.data.local;

import android.net.Uri;
import android.provider.BaseColumns;

import com.udacity.fasttrack.popularmovies.BuildConfig;

/**
 * Created by codedentwickler on 4/24/17.
 */

public final class FavouritesPersistenceContract {

    static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private FavouritesPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static class FavoriteEntry implements BaseColumns {

        private static Uri.Builder builder = new Uri.Builder()
                .scheme("content").authority(CONTENT_AUTHORITY);

        static final Uri BASE_CONTENT_URI = Uri.parse(builder.build().toString());

        private static final String PATH_FAVOURITE = "favourite";
        static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FAVOURITE);

        static final String TABLE_NAME = "favourite";

        static final String _ID = BaseColumns._ID;

        public static final String COLUMN_NAME_ID = "id";

        public static final String COLUMN_NAME_POSTER_URL = "poster_path";

        public static final String COLUMN_NAME_OVERVIEW = "overview";

        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";

        public static final String COLUMN_NAME_ORIGINAL_TITLE = "original_title";

        public static final String COLUMN_NAME_BACKDROP_URL = "backdrop_path";

        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";

        public static final String COLUMN_NAME_LOCAL_BACKDROP_URL = "local_backdrop_path";

        public static final String COLUMN_NAME_LOCAL_POSTER_URL = "local_poster_path";


        static String[] FAVOURITES_COLUMNS = new String[]{
                _ID,
                COLUMN_NAME_ID,
                COLUMN_NAME_POSTER_URL,
                COLUMN_NAME_OVERVIEW,
                COLUMN_NAME_RELEASE_DATE,
                COLUMN_NAME_ORIGINAL_TITLE,
                COLUMN_NAME_BACKDROP_URL,
                COLUMN_NAME_VOTE_AVERAGE,
                COLUMN_NAME_LOCAL_BACKDROP_URL,
                COLUMN_NAME_LOCAL_POSTER_URL
        };
    }

}
