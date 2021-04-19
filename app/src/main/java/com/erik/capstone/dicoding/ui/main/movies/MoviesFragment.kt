package com.erik.capstone.dicoding.ui.main.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.erik.capstone.core.data.Resource
import com.erik.capstone.core.ui.MovieAdapter
import com.erik.capstone.core.utils.SnackBar
import com.erik.capstone.dicoding.R
import com.erik.capstone.dicoding.databinding.FragmentMoviesBinding
import com.erik.capstone.dicoding.ui.main.movies.detail.MovieDetailActivity
import com.erik.capstone.dicoding.ui.main.movies.detail.MovieDetailActivity.Companion.movieId
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private val moviesViewModel: MoviesViewModel by viewModels()

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter = MovieAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            setUpViewModel()
            movieAdapter.onItemClick = { movie ->
                val intent = Intent(activity, MovieDetailActivity::class.java)
                intent.putExtra(movieId, movie.movieId)
                startActivity(intent)
            }
        }
    }

    private fun setUpViewModel() {
        with(binding?.recyclerView) {
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
        }

        getAllMovie()

        binding?.searchMovie?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                moviesViewModel.setDebounceDuration(false)
                lifecycleScope.launch {
                    query?.let {
                        moviesViewModel.queryChannel.send(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    getAllMovie()
                } else {
                    lifecycleScope.launch {
                        newText.let {
                            moviesViewModel.queryChannel.send(it)
                        }
                    }
                }
                return true
            }

        })

        getSearchMovie()
    }

    private fun getAllMovie() {
        with(binding) {
            moviesViewModel.movie.observe(viewLifecycleOwner, { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> this?.loadingItem?.loadingItem?.startShimmer()
                        is Resource.Success -> {
                            this?.loadingItem?.loadingItem?.stopShimmer()
                            this?.loadingItem?.loadingItem?.visibility = View.GONE
                            movieAdapter.setData(movie.data)
                        }
                        is Resource.Error -> {
                            this?.loadingItem?.loadingItem?.stopShimmer()
                            this?.loadingItem?.loadingItem?.visibility = View.GONE
                            binding?.root?.let {
                                SnackBar.showSnackBar(
                                    it,
                                    resources.getString(R.string.something_wrong)
                                )
                            }
                        }
                    }
                }
            })
        }
    }

    private fun getSearchMovie() {
        with(binding) {
            moviesViewModel.searchResult.observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Loading -> this?.loadingItem?.loadingItem?.startShimmer()
                    is Resource.Success -> {
                        this?.loadingItem?.loadingItem?.stopShimmer()
                        this?.loadingItem?.loadingItem?.visibility = View.GONE
                        movieAdapter.setData(it.data)
                    }
                    is Resource.Error -> {
                        this?.loadingItem?.loadingItem?.stopShimmer()
                        this?.loadingItem?.loadingItem?.visibility = View.GONE
                        binding?.let { it1 ->
                            SnackBar.showSnackBar(
                                it1.root,
                                resources.getString(R.string.something_wrong)
                            )
                        }
                    }
                }
            })
        }
    }
}