package com.udacity.fasttrack.popularmovies.data.local;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.utils.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.COLUMN_NAME_ID;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.CONTENT_URI;

/**
 * Created by codedentwickler on 4/27/17.
 */

public class FavouriteService {

    private final Context context;
    private static final String TAG = FavouriteService.class.getSimpleName();
    private final BaseSchedulerProvider mSchedulerProvider;

    @Inject
    public FavouriteService(Context context, BaseSchedulerProvider schedulerProvider) {
        this.context = context.getApplicationContext();
        this.mSchedulerProvider = checkNotNull(schedulerProvider);
    }

    public void addToFavorites(Movie movie) {
        context.getContentResolver().insert(CONTENT_URI, movie.toFavoritesContentValues());
    }

    public void removeFromFavorites(Movie movie) {
        context.getContentResolver().delete(
                CONTENT_URI,
                COLUMN_NAME_ID + " = " + movie.getId(),
                null
        );
    }

    public boolean isFavorite(Movie movie) {
        boolean isFavorite = false;
        Cursor cursor = context.getContentResolver().query(
                CONTENT_URI,
                null,
                COLUMN_NAME_ID + " = " + movie.getId(),
                null,
                null
        );
        if (cursor != null) {
            isFavorite = cursor.getCount() != 0;
            cursor.close();
        }
        return isFavorite;
    }

    public Observable<List<Long>> getFavouritesIdAsObservables() {
        return makeObservable(getIdsFromDatabase())
                .subscribeOn(mSchedulerProvider.computation());
    }

    private static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create( subscriber -> {
                    try {
                        subscriber.onNext(func.call());
                    } catch (Exception ex) {
                        Log.e(TAG, "Error reading from the database", ex);
                    }
                });
    }

    private Callable<List<Long>> getIdsFromDatabase(){

        return () -> {
            Cursor cursor;
            try {
                cursor = context.getContentResolver().query(
                        CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );
                int columnIndex = cursor.getColumnIndex(COLUMN_NAME_ID);
                List<Long> favouriteIds = new ArrayList<>(cursor.getCount());

                while (cursor.moveToNext()){
                    favouriteIds.add(cursor.getLong(columnIndex));
                }
                cursor.close();
                return favouriteIds;
            }
            catch (NullPointerException err){
                Log.e(TAG, err.getMessage(),err);
            }

            return new ArrayList<>(0);

        };
    }

}