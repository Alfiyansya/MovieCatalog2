package com.alfian.moviecatalog2.ui.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alfian.moviecatalog2.data.entity.Show
import com.alfian.moviecatalog2.data.source.repo.ShowRepository

class ShowViewModel(private val showRepo : ShowRepository) : ViewModel() {
    fun getMovies() : LiveData<List<Show>> = showRepo.getMovies()
    fun getTvShows() : LiveData<List<Show>> = showRepo.getTvShows()
}