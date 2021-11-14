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
import java.util.ArrayList

class ShowRepository(private val remoteDataSource: RemoteDataSource) :
    ShowDataSource {
    private var genre: String = ""
    override fun getMovies(): LiveData<List<Show>> {
        val listMoviesResult = MutableLiveData<List<Show>>()
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
                    listMoviesResult.postValue(movieList)
                }
            })
        }
        return listMoviesResult
    }

    override fun getDetailMovie(id: Int?): LiveData<Show> {
        val detailMovieResult = MutableLiveData<Show>()
        CoroutineScope(IO).launch {
            remoteDataSource.getDetailMovie(id, object : RemoteDataSource.LoadDetailMovieCallback {
                override fun onDetailMovieReceived(movie: Movie) {
                    movie.genres?.forEachIndexed { index, data ->
                        if (index < 1) genre += "${data.name}"
                        else if (index >= 1) genre += ", ${data.name}"
                    }
                    val detailMovie = Show(
                        movie.id.toString(),
                        movie.title,
                        genre,
                        movie.overview,
                        movie.posterPath.toString(),
                        movie.voteAverage
                    )
                    detailMovieResult.postValue(detailMovie)
                }


            })
        }
        return detailMovieResult
    }

    override fun getTvShows(): LiveData<List<Show>> {
        val listTvShowsResult = MutableLiveData<List<Show>>()
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
                    listTvShowsResult.postValue(tvShowList)
                }

            })
        }
        return listTvShowsResult
    }

    override fun getDetailTvShow(id: Int?): LiveData<Show> {
        val detailTvShowResult = MutableLiveData<Show>()
        CoroutineScope(IO).launch {
            remoteDataSource.getDetailTvShow(
                id,
                object : RemoteDataSource.LoadDetailTvShowCallback {
                    override fun onDetailTvShowCallback(tvShow: TvShow) {
                        tvShow.genres?.forEachIndexed { index, data ->
                            if (index < 1) genre += "${data.name}"
                            else if (index >= 1) genre += ", ${data.name}"
                        }
                        val detailTvShow = Show(
                            tvShow.id.toString(),
                            tvShow.name,
                            genre,
                            tvShow.overview,
                            tvShow.posterPath,
                            tvShow.voteAverage
                        )
                        detailTvShowResult.postValue(detailTvShow)
                    }

                })
        }
        return detailTvShowResult
    }
}