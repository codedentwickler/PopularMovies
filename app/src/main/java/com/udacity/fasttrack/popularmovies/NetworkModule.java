package com.udacity.fasttrack.popularmovies;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.udacity.fasttrack.popularmovies.data.MovieRepository;
import com.udacity.fasttrack.popularmovies.data.MovieRepositoryImplementation;
import com.udacity.fasttrack.popularmovies.data.local.FavouriteService;
import com.udacity.fasttrack.popularmovies.data.remote.MovieDbRestService;
import com.udacity.fasttrack.popularmovies.utils.schedulers.BaseSchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.udacity.fasttrack.popularmovies.utils.Utils.HttpUtils.BASE_URL;

/**
 * Created by codedentwickler on 4/28/17.
 */


@Module
public class NetworkModule {

    @Provides
    @Singleton
    Cache providesOkHttpCache(Application application) {
        final int CACHE_SIZE = 10 * 1024 * 1024;    // 10 MB

        return new Cache(application.getCacheDir(), CACHE_SIZE);
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(Cache cache) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache);

        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    MovieDbRestService providesMovieDbRestService(Retrofit retrofit) {
        return retrofit.create(MovieDbRestService.class);
    }

    @Provides
    @Singleton
    public MovieRepository providesMovieRepository
            (FavouriteService favouriteService, MovieDbRestService movieDbRestService) {
        return new MovieRepositoryImplementation(movieDbRestService, favouriteService);
    }

    @Provides
    @Singleton
    public FavouriteService providesFavouritesService(Application application,
                                                      BaseSchedulerProvider schedulerProvider) {
        return new FavouriteService(application.getApplicationContext(), schedulerProvider);
    }

}