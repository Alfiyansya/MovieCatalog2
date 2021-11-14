package com.alfian.moviecatalog2.data.source.remote

import com.alfian.moviecatalog2.data.source.remote.api.ApiService
import com.alfian.moviecatalog2.data.source.remote.response.Movie
import com.alfian.moviecatalog2.data.source.remote.response.TvShow
import com.alfian.moviecatalog2.util.EspressoIdlingResource
import retrofit2.await

class RemoteDataSource(private val api: ApiService) {
    suspend fun getMovies(callback: LoadMoviesCallback) {
        try {
            EspressoIdlingResource.increment()
            api.getMovies().await().result?.let { listMovie ->
                callback.onAllMoviesReceived(listMovie)
                EspressoIdlingResource.decrement()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getDetailMovie(id: Int?, callback: LoadDetailMovieCallback) {
        try {
            EspressoIdlingResource.increment()
            api.getDetailMovie(id).await().let { detailMovie ->
                callback.onDetailMovieReceived(detailMovie)
                EspressoIdlingResource.decrement()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getTvShows(callback: LoadTvShowsCallback) {
        try {
            EspressoIdlingResource.increment()
            api.getTvShows().await().result?.let { listTvShow ->
                callback.onAllTvShowsReceived(listTvShow)
                EspressoIdlingResource.decrement()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getDetailTvShow(id: Int?, callback: LoadDetailTvShowCallback) {
        try {
            EspressoIdlingResource.increment()
            api.getDetailTvShow(id).await().let { detailTvShow ->
                callback.onDetailTvShowCallback(detailTvShow)
                EspressoIdlingResource.decrement()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface LoadMoviesCallback {
        fun onAllMoviesReceived(movies: List<Movie>)
    }

    interface LoadDetailMovieCallback {
        fun onDetailMovieReceived(movie: Movie)
    }

    interface LoadTvShowsCallback {
        fun onAllTvShowsReceived(tvShows: List<TvShow>)
    }

    interface LoadDetailTvShowCallback {
        fun onDetailTvShowCallback(tvShow: TvShow)
    }

}