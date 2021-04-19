package com.erik.capstone.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.erik.capstone.core.ui.FavoriteAdapter
import com.erik.capstone.dicoding.di.FavoriteModuleDependencies
import com.erik.capstone.dicoding.ui.main.movies.detail.MovieDetailActivity
import com.erik.capstone.favorite.databinding.FragmentLibraryBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class LibraryFragment : Fragment() {
    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: LibraryViewModel by viewModels { factory }

    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFavoriteComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        favoriteAdapter = FavoriteAdapter()
        favoriteAdapter.onItemClick = { movie ->
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.movieId, movie.id)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerViewFavorite?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }

        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeData() {
        with(binding) {
            viewModel.data.observe(viewLifecycleOwner, {
                favoriteAdapter.setData(it)
                if (it.isNotEmpty()) {
                    favoriteAdapter.setData(it)
                } else {
                    this?.tvNoData?.visibility = View.VISIBLE
                }
            })
        }
    }
}