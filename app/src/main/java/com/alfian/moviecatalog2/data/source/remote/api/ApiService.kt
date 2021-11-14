package com.alfian.moviecatalog2.data.source.remote.api

import com.alfian.moviecatalog2.BuildConfig
import com.alfian.moviecatalog2.data.source.remote.response.ListResponse
import com.alfian.moviecatalog2.data.source.remote.response.Movie
import com.alfian.moviecatalog2.data.source.remote.response.TvShow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    fun getMovies(
        @Query("api_key") apiKey:String = BuildConfig.TMDB_API_KEY
    ): Call<ListResponse<Movie>>
    @GET("tv/airing_today")
    fun getTvShows(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<ListResponse<TvShow>>
    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") id: Int?,
        @Query("api_key") apiKey:String = BuildConfig.TMDB_API_KEY
    ): Call<Movie>
    @GET("tv/{tv_id}")
    fun getDetailTvShow(
        @Path("tv_id") id: Int?,
        @Query("api_key") apiKey:String = BuildConfig.TMDB_API_KEY

    ): Call<TvShow>

}