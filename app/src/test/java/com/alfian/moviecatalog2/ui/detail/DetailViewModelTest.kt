package com.alfian.moviecatalog2.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alfian.moviecatalog2.data.entity.Show
import com.alfian.moviecatalog2.data.source.repo.ShowRepository
import com.alfian.moviecatalog2.util.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val dummyTvShow = DataDummy.generateDummyTvShows()[0]

    private val movieId = dummyMovie.id
    private val tvShowId = dummyTvShow.id

    private lateinit var viewModel: DetailViewModel

    @Mock
    private lateinit var detailRepo: ShowRepository

    @Mock
    private lateinit var observer: Observer<Show>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(detailRepo)
    }

    @Test
    fun getDetailMovieTest() {
        val movie = MutableLiveData<Show>()
        movie.value = dummyMovie

        Mockito.`when`(movieId.toInt().let { detailRepo.getDetailMovie(it) }).thenReturn(movie)

        val movieData = movieId.toInt().let { viewModel.getDetailMovie(it).value } as Show

        verify(detailRepo).getDetailMovie(movieId.toInt())

        assertNotNull(movie)
        assertEquals(dummyMovie.id, movieData.id)
        assertEquals(dummyMovie.title, movieData.title)
        assertEquals(dummyMovie.genre, movieData.genre)
        assertEquals(dummyMovie.description, movieData.description)
        assertEquals(dummyMovie.imagePath, movieData.imagePath)
        assertEquals(dummyMovie.rating, movieData.rating)
        viewModel.getDetailMovie(movieId.toInt()).observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun getDetailTvShowTest() {
        val tvShow = MutableLiveData<Show>()
        tvShow.value = dummyTvShow

        Mockito.`when`(tvShowId.toInt().let { detailRepo.getDetailTvShow(it) }).thenReturn(tvShow)

        val tvShowData = tvShowId.toInt().let { viewModel.getDetailTvShow(it).value } as Show

        verify(detailRepo).getDetailTvShow(tvShowId.toInt())

        assertNotNull(tvShow)
        assertEquals(dummyTvShow.id, tvShowData.id)
        assertEquals(dummyTvShow.title, tvShowData.title)
        assertEquals(dummyTvShow.description, tvShowData.description)
        assertEquals(dummyTvShow.genre, tvShowData.genre)
        assertEquals(dummyTvShow.imagePath, tvShowData.imagePath)
        assertEquals(dummyTvShow.rating, tvShowData.rating)
        viewModel.getDetailTvShow(tvShowId.toInt()).observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }
}