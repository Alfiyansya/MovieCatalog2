package com.alfian.moviecatalog2.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alfian.moviecatalog2.data.entity.Show
import com.alfian.moviecatalog2.data.source.repo.ShowRepository

class DetailViewModel(private val repo: ShowRepository) : ViewModel() {
    fun getDetailMovie(id: Int?): LiveData<Show> = repo.getDetailMovie(id)
    fun getDetailTvShow(id: Int?): LiveData<Show> = repo.getDetailTvShow(id)
}