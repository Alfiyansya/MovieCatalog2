package com.alfian.moviecatalog2.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alfian.moviecatalog2.R
import com.alfian.moviecatalog2.data.entity.Show
import com.alfian.moviecatalog2.databinding.ActivityDetailBinding
import com.alfian.moviecatalog2.util.DataDummy
import com.alfian.moviecatalog2.util.DataDummy.IMAGE_ENDPOINT
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding
    private val detailViewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val id = intent.getStringExtra(EXTRA_ID)
        val key = intent.getStringExtra(EXTRA_KEY)
        if (key == DataDummy.MOVIE) {
            detailViewModel.getDetailMovie(id?.toInt())
                .observe(this@DetailActivity, { detailMovie ->
                    setUpDataMovie(detailMovie)
                })
        } else {
            detailViewModel.getDetailTvShow(id?.toInt())
                .observe(this@DetailActivity, { detailTvShow ->
                    setUpDataTvShow(detailTvShow)
                })
        }
    }

    private fun setUpDataMovie(show: Show?) {
        with(binding) {
            this?.detailPoster?.let {
                Glide.with(this@DetailActivity)
                    .load(IMAGE_ENDPOINT + show?.imagePath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(it)
            }

            this?.detailTitle?.text = show?.title
            this?.detailGenre?.text = show?.genre
            this?.detailRating?.text = show?.rating.toString()
            this?.detailOverviewValue?.text = show?.description


        }
    }

    private fun setUpDataTvShow(show: Show?) {
        with(binding) {
            this?.detailPoster?.let {
                Glide.with(this@DetailActivity)
                    .load(IMAGE_ENDPOINT + show?.imagePath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(it)
            }
            this?.detailTitle?.text = show?.title
            this?.detailGenre?.text = show?.genre
            this?.detailRating?.text = show?.rating.toString()
            this?.detailOverviewValue?.text = show?.description
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_ID = "id"
        const val EXTRA_KEY = "key"
    }
}