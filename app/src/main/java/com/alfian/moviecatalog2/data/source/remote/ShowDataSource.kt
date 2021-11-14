package com.alfian.moviecatalog2.data.source.remote

import androidx.lifecycle.LiveData
import com.alfian.moviecatalog2.data.entity.Show

interface ShowDataSource {
    fun getMovies(): LiveData<List<Show>>
    fun getDetailMovie(id: Int?): LiveData<Show>
    fun getTvShows(): LiveData<List<Show>>
    fun getDetailTvShow(id: Int?): LiveData<Show>
}