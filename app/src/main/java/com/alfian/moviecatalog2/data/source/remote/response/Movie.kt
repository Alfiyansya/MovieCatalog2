package com.alfian.moviecatalog2.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int? = null,
    val title: String? = null,
    val overview: String? = null,
    val genres: List<Genre>? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
)
