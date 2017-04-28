package com.udacity.fasttrack.popularmovies.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.CONTENT_AUTHORITY;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.FAVOURITES_COLUMNS;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.TABLE_NAME;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry._ID;

/**
 * Created by codedentwickler on 4/27/17.
 */

public class FavouriteProvider extends ContentProvider {

    private static final String TAG = FavouriteProvider.class.getSimpleName();
    private static final int FAVOURITES = 10;
    private static final int FAVOURITE_ID = 11;
    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, TABLE_NAME, FAVOURITES);
        sUriMatcher.addURI(CONTENT_AUTHORITY, TABLE_NAME + "/#", FAVOURITE_ID);

    }

    private FavouriteDbHelper mDbHelper;

    @Override
    public boolean onCreate() {

        mDbHelper = new FavouriteDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor retCursor;

        int match = sUriMatcher.match(uri);

        switch (match) {

            case FAVOURITES:

                retCursor = database.query(
                        TABLE_NAME,
                        FAVOURITES_COLUMNS,
                        null, null,
                        null,
                        null,
                        null);
                break;

            case FAVOURITE_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                retCursor = database.query(
                        TABLE_NAME,
                        FAVOURITES_COLUMNS,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                Log.d(TAG, "Value of retCursor position" + String.valueOf(retCursor.getPosition()));

                break;
            default:
                throw new IllegalArgumentException("Cannot perform query on unknown URI" + uri);

        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase database = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match) {

            case FAVOURITES:

                long id = database.insert(TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);

            default:
                throw new IllegalArgumentException("Insertion Cannot be done on uri" + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVOURITES:
                int id = database.delete(TABLE_NAME, null, null);
                getContext().getContentResolver().notifyChange(uri, null);
                return id;

            case FAVOURITE_ID:

                int id2 = database.delete(TABLE_NAME, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return id2;
            default:
                throw new IllegalArgumentException("Deletion cannot be done on unknown uri" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        switch (match) {
            case FAVOURITE_ID:
                int id = database.update(TABLE_NAME, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return id;

            default:
                throw new IllegalArgumentException("Cannot update row with unknown uri" + uri);
        }
    }
}
