package com.udacity.fasttrack.popularmovies.data.local;

import android.net.Uri;
import android.provider.BaseColumns;

import com.udacity.fasttrack.popularmovies.BuildConfig;

/**
 * Created by codedentwickler on 4/24/17.
 */

public final class FavouritesPersistenceContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private FavouritesPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static class FavoriteEntry implements BaseColumns {

        private static Uri.Builder builder = new Uri.Builder()
                .scheme("content").authority(CONTENT_AUTHORITY);

        public static final Uri BASE_CONTENT_URI = Uri.parse(builder.build().toString());

        private static final String PATH_PET = "favourite";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PET);

        public static final String TABLE_NAME = "favourite";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_ID = "id";


        public static String[] FAVOURITES_COLUMNS = new String[]{
                FavoriteEntry._ID,
                FavoriteEntry.COLUMN_NAME_ID,
                FavouritesPersistenceContract.FavoriteEntry.COLUMN_NAME_TITLE };
        
    }

}
