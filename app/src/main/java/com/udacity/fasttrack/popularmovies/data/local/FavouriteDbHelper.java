package com.udacity.fasttrack.popularmovies.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by codedentwickler on 4/27/17.
 */

public class FavouriteDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "favourite.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String NOT_NULL_CONSTRAINT = " NOT NULL";

    private static final String UNIQUE_CONSTRAINT = " UNIQUE";


    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FavouritesPersistenceContract.FavoriteEntry.TABLE_NAME + " (" +
                    FavouritesPersistenceContract.FavoriteEntry._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    FavouritesPersistenceContract.FavoriteEntry.COLUMN_NAME_ID
                    + TEXT_TYPE + UNIQUE_CONSTRAINT + NOT_NULL_CONSTRAINT + COMMA_SEP +
                    FavouritesPersistenceContract.FavoriteEntry.COLUMN_NAME_TITLE + TEXT_TYPE +
                    " )";

    public FavouriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
