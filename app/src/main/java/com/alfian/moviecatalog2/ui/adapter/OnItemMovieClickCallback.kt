package com.alfian.moviecatalog2.ui.adapter

import com.alfian.moviecatalog2.data.entity.Show

interface OnItemMovieClickCallback {
    fun onItemClicked(show : Show)
}