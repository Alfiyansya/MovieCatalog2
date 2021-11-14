package com.alfian.moviecatalog2.ui.show

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.alfian.moviecatalog2.util.DataDummy
import com.alfian.moviecatalog2.data.entity.Show
import com.alfian.moviecatalog2.databinding.FragmentShowBinding
import com.alfian.moviecatalog2.ui.adapter.MovieAdapter
import com.alfian.moviecatalog2.ui.adapter.OnItemMovieClickCallback
import com.alfian.moviecatalog2.ui.adapter.OnItemTvShowClickCallback
import com.alfian.moviecatalog2.ui.adapter.TvShowAdapter
import com.alfian.moviecatalog2.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowFragment : Fragment() {

    private var _binding: FragmentShowBinding? = null
    private val binding get() = _binding
    private val showViewModel by viewModel<ShowViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        // Inflate the layout for this fragment
        _binding = FragmentShowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        if (index == 1) {
            showViewModel.getMovies().observe(viewLifecycleOwner) { movies ->
                setUpDataMovie(movies)
            }
        } else {
            showViewModel.getTvShows().observe(viewLifecycleOwner) { tvShows ->
                setUpDataTvShow(tvShows)
            }
        }
    }

    private fun setUpDataMovie(movies: List<Show>) {
        val movieAdapter = MovieAdapter()
        movieAdapter.setMovies(movies)
        with(binding?.rvShow) {
            this?.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
        }
        movieAdapter.setOnItemClickCallback(object : OnItemMovieClickCallback {
            override fun onItemClicked(show: Show) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, show.id)
                intent.putExtra(DetailActivity.EXTRA_KEY, DataDummy.MOVIE)
                startActivity(intent)
            }

        })
    }

    private fun setUpDataTvShow(tvShows: List<Show>) {
        val tvShowAdapter = TvShowAdapter()
        tvShowAdapter.setTvShows(tvShows)
        with(binding?.rvShow) {
            this?.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            this?.setHasFixedSize(true)
            this?.adapter = tvShowAdapter
        }
        tvShowAdapter.setOnItemClickCallback(object : OnItemTvShowClickCallback {
            override fun onItemClicked(show: Show) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, show.id)
                intent.putExtra(DetailActivity.EXTRA_KEY, DataDummy.TVSHOW)
                startActivity(intent)
            }
        })
    }


    companion object {

        private const val ARG_SECTION_NUMBER = "section number"

        @JvmStatic
        fun newInstance(index: Int) =
            ShowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}