package com.alfian.moviecatalog2.data.source.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alfian.moviecatalog2.data.entity.Show
import com.alfian.moviecatalog2.data.source.remote.RemoteDataSource
import com.alfian.moviecatalog2.data.source.remote.ShowDataSource
import com.alfian.moviecatalog2.data.source.remote.response.Movie
import com.alfian.moviecatalog2.data.source.remote.response.TvShow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class FakeShowRepository(private val remoteDataSource: RemoteDataSource) :
    ShowDataSource {

    private var genres: String = ""

    override fun getMovies(): LiveData<List<Show>> {
        val listMovieResult = MutableLiveData<List<Show>>()
        CoroutineScope(IO).launch {
            remoteDataSource.getMovies(object : RemoteDataSource.LoadMoviesCallback {
                override fun onAllMoviesReceived(movies: List<Movie>) {
                    val movieList = ArrayList<Show>()
                    for (response in movies) {
                        val movie = Show(
                            id = response.id.toString(),
                            title = response.title.toString(),
                            imagePath = response.posterPath.toString(),
                            rating = response.voteAverage
                        )
                        movieList.add(movie)
                    }
                    listMovieResult.postValue(movieList)
                }

            })
        }
        return listMovieResult
    }

    override fun getDetailMovie(id: Int?): LiveData<Show> {
        val movieDetailResult = MutableLiveData<Show>()
        CoroutineScope(IO).launch {
            remoteDataSource.getDetailMovie(
                id,
                object : RemoteDataSource.LoadDetailMovieCallback {
                    override fun onDetailMovieReceived(
                        movie : Movie
                    ) {
                        movie.genres?.forEachIndexed { index, data ->
                            if (index < 1) genres += "${data.name}"
                            else if (index >= 1) genres += ", ${data.name}"
                        }
                        val movieData = Show(
                            movie.id.toString(),
                            movie.title,
                            genres,
                            movie.overview,
                            movie.posterPath.toString(),
                            movie.voteAverage
                        )
                        movieDetailResult.postValue(movieData)
                    }
                })
        }
        return movieDetailResult
    }

    override fun getTvShows(): LiveData<List<Show>> {
        val listTVsResult = MutableLiveData<List<Show>>()
        CoroutineScope(IO).launch {
            remoteDataSource.getTvShows(object : RemoteDataSource.LoadTvShowsCallback {
                override fun onAllTvShowsReceived(tvShows: List<TvShow>) {
                    val tvShowList = ArrayList<Show>()
                    for (response in tvShows) {
                        val tvShow = Show(
                            id = response.id.toString(),
                            title = response.name.toString(),
                            imagePath = response.posterPath.toString(),
                            rating = response.voteAverage
                        )
                        tvShowList.add(tvShow)
                    }
                    listTVsResult.postValue(tvShowList)
                }
            })
        }
        return listTVsResult
    }

    override fun getDetailTvShow(id: Int?): LiveData<Show> {
        val tvDetailResult = MutableLiveData<Show>()
        CoroutineScope(IO).launch {
            remoteDataSource.getDetailTvShow(id, object : RemoteDataSource.LoadDetailTvShowCallback {
                override fun onDetailTvShowCallback(tvShow: TvShow) {
                    tvShow.genres?.forEachIndexed { index, data ->
                        if (index < 1) genres += "${data.name}"
                        else if (index >= 1) genres += ", ${data.name}"
                    }
                    val tvShowData = Show(
                        tvShow.id.toString(),
                        tvShow.name,
                        genres,
                        tvShow.overview,
                        tvShow.posterPath.toString(),
                        tvShow.voteAverage,
                    )
                    tvDetailResult.postValue(tvShowData)
                }

            })
        }
        return tvDetailResult
    }

}