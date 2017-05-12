package com.udacity.fasttrack.popularmovies.data.local;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.utils.schedulers.BaseSchedulerProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.COLUMN_NAME_ID;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.CONTENT_URI;
import static com.udacity.fasttrack.popularmovies.data.local.FavouritesPersistenceContract.FavoriteEntry.FAVOURITES_COLUMNS;

/**
 * Created by codedentwickler on 4/27/17.
 */

public class FavouriteService {

    private final Context context;
    private static final String TAG = FavouriteService.class.getSimpleName();
    private final BaseSchedulerProvider mSchedulerProvider;

    public FavouriteService(Context context, BaseSchedulerProvider schedulerProvider) {
        this.context = context.getApplicationContext();
        this.mSchedulerProvider = checkNotNull(schedulerProvider);
    }

    public void addToFavorites(Movie movie) {

        AsyncTask.execute(() -> {
            //TODO your background code

            String  localPosterUri = saveImageToInternalStorage(movie);
            String  localBackdropUri = saveImageToInternalStorage(movie);

            context.getContentResolver().insert(CONTENT_URI,
                    movie.toFavoritesContentValues(localPosterUri, localBackdropUri));
        });

    }

    private String saveImageToInternalStorage(Movie movie){

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context).
                    load("Url of your image").
                    asBitmap().
                    into(-1, -1).
                    get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ContextWrapper cw = new ContextWrapper(context);

        File directory = cw.getDir("images", Context.MODE_PRIVATE);

        File file = new File(directory, movie.getOriginalTitle() + ".jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(file);

            // Use the compress method on the BitMap object to write image to the OutputStream
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("absolutepath ", directory.getAbsolutePath());
        return directory.getAbsolutePath();
    }

    public void removeFromFavorites(Movie movie) {
            context.getContentResolver().delete(
                CONTENT_URI,
                COLUMN_NAME_ID + "=" + movie.getId(),
                null
        );
    }

    public boolean isFavorite(Movie movie) {
        boolean isFavorite = false;
        Cursor cursor = context.getContentResolver().query(
                CONTENT_URI,
                new String[]{COLUMN_NAME_ID},
                COLUMN_NAME_ID + "=" + movie.getId(),
                null,
                null
        );
        Log.e(TAG, COLUMN_NAME_ID + " = " + movie.getId());
        if (cursor != null ) {
            isFavorite = cursor.getCount() != 0;
            cursor.close();
        }
        return isFavorite;
    }

    public Observable<List<Movie>> getFavouritesAsObservables() {
        return makeObservable(getFavouritesFromDatabase());
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

    private Callable<List<Movie>> getFavouritesFromDatabase(){

        return () -> {
            Cursor cursor;
            try {
                cursor = context.getContentResolver().query(
                        CONTENT_URI,
                        FAVOURITES_COLUMNS,
                        null,
                        null,
                        null
                );
                List<Movie> favourites = new ArrayList<>();

                while (cursor.moveToNext()){
                    favourites.add(Movie.parseCursorToMovie(cursor));
                }
                cursor.close();
                Log.e(TAG, String.valueOf(favourites.size()) + " was fetched from Favourites table");

                return favourites;
            }
            catch (NullPointerException err){
                Log.e(TAG, err.getMessage(),err);
            }

            return new ArrayList<>(0);
        };
    }
}


//        Bitmap bitmap = ((GlideBitmapDrawable)drawable).getBitmap();
//
//        ContextWrapper wrapper = new ContextWrapper(context);
//
//        File file = wrapper.getDir("images",MODE_PRIVATE);
//
//        file = new File(file, movie.getOriginalTitle() + ".jpg");
//
//        try {
//
//            OutputStream stream = new FileOutputStream(file);
//
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
//
//            stream.flush();
//            stream.close();
//
//        } catch (FileNotFoundException e) {
//
//            e.printStackTrace();
//            throw new IllegalArgumentException("Please check if this file exist "
//                    + file.getName());
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Parse and Return the gallery image url to uri
//
//        return Uri.parse(file.getAbsolutePath());