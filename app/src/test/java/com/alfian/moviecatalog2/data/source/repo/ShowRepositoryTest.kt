package com.alfian.moviecatalog2.data.source.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alfian.moviecatalog2.data.source.remote.RemoteDataSource
import com.alfian.moviecatalog2.data.source.remote.api.ApiBuilder
import com.alfian.moviecatalog2.data.source.remote.response.Movie
import com.alfian.moviecatalog2.data.source.remote.response.TvShow
import com.alfian.moviecatalog2.ui.helper.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class ShowRepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val repo = FakeShowRepository(remote)

    private var listMovie =ArrayList<Movie>() as List<Movie>
    private var listTvShow = ArrayList<TvShow>() as List<TvShow>

    private var movieId: Int = 0
    private var tvShowId: Int = 0

    private var movieResponseDetail: Movie? = null
    private var tvShowResponseDetail: TvShow? = null

    @Before
    fun getMoviesTest(){

        try {
            ApiBuilder.createService().getMovies().execute().body()?.result.let{
                if (it != null) {
                    listMovie = it
                }
            }
            movieId = listMovie[0].id!!
            ApiBuilder.createService().getDetailMovie(movieId).execute().body()
                ?.let { movie ->
                    movieResponseDetail = movie
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    @Before
    fun getTvShowTest() {
        ApiBuilder.createService().getTvShows().execute().body()?.result.let {
            if (it != null) {
                listTvShow = it
            }
        }
        tvShowId = listTvShow[0].id!!
        ApiBuilder.createService().getDetailTvShow(tvShowId).execute().body()?.let {
            tvShowResponseDetail = it
        }
    }

    // Movie Testing
    @Test
    fun getMovies() {
        runBlocking {
            doAnswer {
                (it.arguments[0] as RemoteDataSource.LoadMoviesCallback).onAllMoviesReceived(
                    listMovie
                )
                null
            }.`when`(remote).getMovies(any())
        }

        val data = LiveDataTestUtil.getValue(repo.getMovies())

        runBlocking {
            verify(remote).getMovies(any())
        }

        Assert.assertNotNull(data)
        assertEquals(listMovie.size.toLong(), data.size.toLong())
    }

    // Detail Movie Testing
    @Test
    fun getMovieDetail() {
        runBlocking {
            doAnswer {
                (it.arguments[1] as RemoteDataSource.LoadDetailMovieCallback).onDetailMovieReceived(
                    movieResponseDetail!!
                )
                null
            }.`when`(remote).getDetailMovie(eq(movieId), any())
        }

        val data =
            LiveDataTestUtil.getValue(repo.getDetailMovie(movieId))

        runBlocking {
            verify(remote).getDetailMovie(eq(movieId), any())
        }

        Assert.assertNotNull(data)
        assertEquals(movieResponseDetail!!.id.toString(), data.id)
    }

    // Tv Show Testing
    @Test
    fun getTvShow() {
        runBlocking {
            doAnswer {
                (it.arguments[0] as RemoteDataSource.LoadTvShowsCallback).onAllTvShowsReceived(
                    listTvShow
                )
                null
            }.`when`(remote).getTvShows(any())
        }

        val data = LiveDataTestUtil.getValue(repo.getTvShows())

        runBlocking {
            verify(remote).getTvShows(any())
        }

        Assert.assertNotNull(data)
        assertEquals(listTvShow.size.toLong(), data.size.toLong())
    }

    // Detail Tv Show  Testing
    @Test
    fun getDetailTvShow() {
        runBlocking {
            doAnswer {
                (it.arguments[1] as RemoteDataSource.LoadDetailTvShowCallback).onDetailTvShowCallback(
                    tvShowResponseDetail!!
                )
                null
            }.`when`(remote).getDetailTvShow(eq(tvShowId), any())
        }

        val data =
            LiveDataTestUtil.getValue(repo.getDetailTvShow(tvShowId))

        runBlocking {
            verify(remote).getDetailTvShow(eq(tvShowId), any())
        }

        Assert.assertNotNull(data)
        assertEquals(tvShowResponseDetail!!.id.toString(), data.id)
    }
}