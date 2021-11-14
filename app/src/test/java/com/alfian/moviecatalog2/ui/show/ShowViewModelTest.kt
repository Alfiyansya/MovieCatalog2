package com.alfian.moviecatalog2.ui.show

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alfian.moviecatalog2.data.entity.Show
import com.alfian.moviecatalog2.data.source.repo.ShowRepository
import com.alfian.moviecatalog2.util.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShowViewModelTest{
    private lateinit var showViewModel : ShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo :ShowRepository

    @Mock
    private lateinit var observer: Observer<List<Show>>

    @Before
    fun setUp(){
        showViewModel = ShowViewModel(repo)
    }
    @Test
    fun getMovies(){
        val dummyMovie = DataDummy.generateDummyMovies()
        val movie = MutableLiveData<List<Show>>()
        movie.value = dummyMovie
        Mockito.`when`(repo.getMovies()).thenReturn(movie)
        val listMovie = showViewModel.getMovies().value
        assertNotNull(listMovie)
        assertEquals(10, listMovie?.size)
        Mockito.verify(repo).getMovies()
        showViewModel.getMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovie)
    }
    @Test
    fun getTvShows() {
        val dummyTvShow = DataDummy.generateDummyTvShows()
        val tvShow = MutableLiveData<List<Show>>()
        tvShow.value = dummyTvShow
        Mockito.`when`(repo.getTvShows()).thenReturn(tvShow)
        val listTvShow = showViewModel.getTvShows().value
        assertNotNull(listTvShow)
        assertEquals(10, listTvShow?.size)
        Mockito.verify(repo).getTvShows()

        showViewModel.getTvShows().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTvShow)
    }
}