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

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {
    private var listTvShow = ArrayList<Show>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemShowBinding =
            ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemShowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvShow = listTvShow[position]
        holder.bind(tvShow)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listTvShow[position]) }
    }

    override fun getItemCount(): Int = listTvShow.size
    private lateinit var onItemClickCallback: OnItemTvShowClickCallback

    class ViewHolder(private val binding: ItemShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: Show) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(IMAGE_ENDPOINT + tvShow.imagePath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(itemPoster)
                itemTitle.text = tvShow.title
                itemRating.text = tvShow.rating.toString()
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemTvShowClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setTvShows(tvShows: List<Show>) {
        if (tvShows.isNullOrEmpty()) return
        this.listTvShow.clear()
        this.listTvShow.addAll(tvShows)
    }
}