package com.udacity.fasttrack.popularmovies.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry;

/**
 * Created by codedentwickler on 4/27/17.
 */

public class FavouriteDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "favourite.db";

    private static final String PRIMARY_KEY_AUTO_INCREMENT = " PRIMARY KEY AUTOINCREMENT, ";

    private static final String TEXT = " TEXT, ";

    private static final String TEXT_NO_COMMA = " TEXT";

    private static final String NOT_NULL = " NOT NULL, ";

    private static final String UNIQUE = " UNIQUE";

    private static final String INTEGER = " INTEGER";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FavoriteEntry.TABLE_NAME + " (" +
                    FavoriteEntry._ID + INTEGER + PRIMARY_KEY_AUTO_INCREMENT +
                    FavoriteEntry.COLUMN_NAME_ID + INTEGER + UNIQUE + NOT_NULL +
                    FavoriteEntry.COLUMN_NAME_POSTER_URL + TEXT +
                    FavoriteEntry.COLUMN_NAME_BACKDROP_URL + TEXT +
                    FavoriteEntry.COLUMN_NAME_ORIGINAL_TITLE + TEXT +
                    FavoriteEntry.COLUMN_NAME_OVERVIEW + TEXT +
                    FavoriteEntry.COLUMN_NAME_RELEASE_DATE + TEXT +
                    FavoriteEntry.COLUMN_NAME_VOTE_AVERAGE + TEXT +
                    FavoriteEntry.COLUMN_NAME_LOCAL_POSTER_URL + TEXT +
                    FavoriteEntry.COLUMN_NAME_LOCAL_BACKDROP_URL + TEXT_NO_COMMA +
                    " );";


    private static final String DROP_TABLE_QUERY =
            " DROP TABLE " + FavoriteEntry.TABLE_NAME+ ";";

    public FavouriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_QUERY);

    }
}
