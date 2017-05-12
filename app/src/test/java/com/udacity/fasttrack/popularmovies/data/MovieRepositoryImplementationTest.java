package com.udacity.fasttrack.popularmovies.data;

import com.udacity.fasttrack.popularmovies.data.remote.MovieDbRestService;
import com.udacity.fasttrack.popularmovies.data.remote.model.Movie;
import com.udacity.fasttrack.popularmovies.data.remote.model.MoviesList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by codedentwickler on 4/15/17.
 */
public class MovieRepositoryImplementationTest {

    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";

    private static final String FAST_AND_FURIOUS = "Fast and Furious 8";
    private static final String JURASIC_WORLD = "Jurasic World";
    private MovieRepository mMovieRepository;

    @Mock
    private MovieDbRestService mMovieDbRestService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mMovieRepository = new MovieRepositoryImplementation(mMovieDbRestService);
    }

    @Test
    public void fetchAllMovies_200OkResponse_InvokesCorrectApiCalls() {

        //Given
        when(mMovieDbRestService.fetchAllMovies(anyString()))
                .thenReturn(Observable.just(moviesList()));

        //When
        TestSubscriber<List<Movie>> testSubscriber = new TestSubscriber<>();
        mMovieRepository.fetchMovies(POPULAR).subscribe(testSubscriber);

        //Then
        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertNoErrors();

        List<List<Movie>> onNextEvents = testSubscriber.getOnNextEvents();
        List<Movie> movies = onNextEvents.get(0);
        Assert.assertEquals(JURASIC_WORLD, movies.get(0).getOriginalTitle());
        Assert.assertEquals(FAST_AND_FURIOUS, movies.get(1).getOriginalTitle());
        verify(mMovieDbRestService).fetchAllMovies(POPULAR);

    }

    @Test
    public void fetchAllMovies_IOExceptionThenSuccess_fetchMoviesRetried() {
        //Given
        when(mMovieDbRestService.fetchAllMovies(anyString()))
                .thenReturn(getIOExceptionError(), Observable.just(moviesList()));


        //When
        TestSubscriber<List<Movie>> testSubscriber = new TestSubscriber<>();
        mMovieRepository.fetchMovies(POPULAR).subscribe(testSubscriber);

        //Then
        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertNoErrors();

        verify(mMovieDbRestService, times(2)).fetchAllMovies(POPULAR);
    }

    @Test
    public void fetchAllMovies_OtherHttpError_fetchMoviesTerminatedWithError() {
        //Given
        when(mMovieDbRestService.fetchAllMovies(anyString()))
                .thenReturn(get403ForbiddenError());

        //When
        TestSubscriber<List<Movie>> subscriber = new TestSubscriber<>();
        mMovieRepository.fetchMovies(POPULAR).subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        verify(mMovieDbRestService).fetchAllMovies(POPULAR);
    }



    private MoviesList moviesList() {

        Movie movie1 = new Movie(JURASIC_WORLD);

        Movie movie2 = new Movie(FAST_AND_FURIOUS);

        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);

        MoviesList moviesList = new MoviesList();
        moviesList.setMovies(movies);

        return moviesList;
    }

    private Observable<MoviesList> get403ForbiddenError() {
        return Observable.error(new HttpException(Response.error(403, ResponseBody.create
                (MediaType.parse("application/json"), "Forbidden"))));
    }

    private Observable getIOExceptionError() {
        return Observable.error(new IOException());
    }

}