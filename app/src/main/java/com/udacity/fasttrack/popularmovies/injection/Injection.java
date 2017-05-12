package com.udacity.fasttrack.popularmovies.injection;


/**
 * Created by codedentwickler on 4/12/17.
 */

import android.app.Application;
import android.content.Context;

import com.udacity.fasttrack.popularmovies.data.MovieRepository;
import com.udacity.fasttrack.popularmovies.data.MovieRepositoryImplementation;
import com.udacity.fasttrack.popularmovies.data.local.FavouriteService;
import com.udacity.fasttrack.popularmovies.data.remote.MovieDbRestService;
import com.udacity.fasttrack.popularmovies.utils.schedulers.BaseSchedulerProvider;
import com.udacity.fasttrack.popularmovies.utils.schedulers.SchedulerProvider;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.udacity.fasttrack.popularmovies.utils.Utils.HttpUtils.BASE_URL;

public final class Injection {

    private static OkHttpClient okHttpClient;
    private static MovieDbRestService movieDbRestService;
    private static Retrofit retrofitInstance;


    private static Application application;

    private static FavouriteService favouriteService;

    private static Application provideApplicationContext(){
        if (application == null) {
            application = new Application();
        }
        return application;
    }

    public static FavouriteService provideFavouriteService(Context context){
        return new FavouriteService(context, provideSchedulerProvider() );
    }

    public static MovieRepository provideMovieRepo(Context context) {
        return new MovieRepositoryImplementation(provideMovieDbRestServiceRestService());
    }

    private static MovieDbRestService provideMovieDbRestServiceRestService() {
        if (movieDbRestService == null) {
            movieDbRestService = getRetrofitInstance().create(MovieDbRestService.class);
        }
        return movieDbRestService;
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            okHttpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        }
        return okHttpClient;
    }

    private static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            Retrofit.Builder retrofit = new Retrofit.Builder().client(Injection.getOkHttpClient()).
                    baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            retrofitInstance = retrofit.build();
        }
        return retrofitInstance;
    }
}
