package com.alfian.moviecatalog2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alfian.moviecatalog2.R
import com.alfian.moviecatalog2.data.entity.Show
import com.alfian.moviecatalog2.databinding.ItemShowBinding
import com.alfian.moviecatalog2.util.DataDummy.IMAGE_ENDPOINT
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var listMovie = ArrayList<Show>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemShowBinding =
            ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemShowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listMovie[position]) }
    }

    override fun getItemCount(): Int = listMovie.size
    private lateinit var onItemClickCallback: OnItemMovieClickCallback

    class ViewHolder(private val binding: ItemShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Show) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(IMAGE_ENDPOINT + movie.imagePath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(itemPoster)
                itemTitle.text = movie.title
                itemRating.text = movie.rating.toString()
            }

        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemMovieClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setMovies(movies: List<Show>) {
        if (movies.isNullOrEmpty()) return
        this.listMovie.clear()
        this.listMovie.addAll(movies)
    }


}