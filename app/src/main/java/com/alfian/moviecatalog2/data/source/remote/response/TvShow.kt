package com.alfian.moviecatalog2.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShow(
    val id: Int? = null,
    val name: String? = null,
    val overview: String? = null,
    val genres: List<Genre>? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,
)