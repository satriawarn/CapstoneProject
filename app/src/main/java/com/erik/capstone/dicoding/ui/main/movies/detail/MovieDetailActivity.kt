package com.erik.capstone.dicoding.ui.main.movies.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.erik.capstone.core.data.Resource
import com.erik.capstone.core.domain.model.MovieDetail
import com.erik.capstone.core.utils.SnackBar
import com.erik.capstone.core.utils.loadImage
import com.erik.capstone.dicoding.BuildConfig
import com.erik.capstone.dicoding.R
import com.erik.capstone.dicoding.databinding.ActivityMovieDetailBinding
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val movieId = "movieId"

        fun fromMinutesToHHmm(minutes: Int): String {
            val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
            val remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)
            return String.format("%2d h %2d min", hours, remainMinutes)
        }

        var movieTitle: String? = null
    }

    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    private lateinit var detailMovieBinding: ActivityMovieDetailBinding

    private lateinit var movieDetail: MovieDetail
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailMovieBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(detailMovieBinding.root)

        setSupportActionBar(detailMovieBinding.toolbar)
        title(supportActionBar!!, "")


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setListener()
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        } else {
            supportActionBar?.setHomeAsUpIndicator(
                ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
            )
            title(supportActionBar!!, "")
        }

        val detailMovie = intent.getIntExtra(movieId, 0)
        showDetailMovie(detailMovie)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun title(actionBar: ActionBar?, title: String) {
        actionBar?.title = title
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setListener() {
        with(detailMovieBinding) {
            appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
                var isShow = true
                var scrollRange = -1
                override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout!!.totalScrollRange
                    }
                    if (scrollRange + verticalOffset == 0) {
                        ctlDetail.title = movieTitle
                        supportActionBar?.setHomeAsUpIndicator(
                            ContextCompat.getDrawable(applicationContext, R.drawable.ic_arrow_back)
                        )
                        isShow = true
                    } else if (isShow) {
                        ctlDetail.title = " "
                        supportActionBar?.setHomeAsUpIndicator(
                            ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.ic_back_overide
                            )
                        )
                        isShow = false
                    }
                }
            })
        }
    }

    private fun showDetailMovie(movieId: Int?) {
        movieId?.let {
            movieDetailViewModel.getDetail(movieId)
            showData()
            observeFavorite(movieId)
        }
    }

    private fun showData() {
        with(detailMovieBinding) {
            lifecycleScope.launch {
                movieDetailViewModel.detail.observe(this@MovieDetailActivity, { result ->
                    when (result) {
                        is Resource.Loading -> {
                            shImage.startShimmer()
                        }
                        is Resource.Success -> {
                            shImage.stopShimmer()
                            shImage.visibility = View.GONE

                            val detailMovie = result.data
                            if (detailMovie != null) {
                                movieDetail = detailMovie
                                showDetail(movieDetail)
                            }
                        }
                        is Resource.Error -> {
                            shImage.stopShimmer()
                            shImage.visibility = View.GONE
                            SnackBar.showSnackBar(
                                detailMovieBinding.root,
                                resources.getString(R.string.something_wrong)
                            )
                        }
                    }
                })
            }
        }
    }

    private fun showDetail(movieDetail: MovieDetail) {
        with(detailMovieBinding) {
            ivImage.loadImage(BuildConfig.BASE_URL_POSTER_PATH_BIG + movieDetail.backdropPath)
        }

        with(detailMovieBinding.contentDetail) {

            movieTitle = movieDetail.title

            rbRatting.rating = movieDetail.voteAverage.toFloat()
            tvRating.text = movieDetail.voteAverage.toString()
            tvCount.text =
                StringBuilder(movieDetail.voteCount.toString() + " Reviews")
            tvName.text = movieDetail.title
            tvTagLine.text = movieDetail.tagline
            tvReleaseDate.text =
                resources.getString(R.string.releasedate, movieDetail.releaseDate)

            tvDescription.text = movieDetail.overview
            tvRuntime.text =
                fromMinutesToHHmm(movieDetail.runtime)
        }
    }

    private fun observeFavorite(id: Int) {
        movieDetailViewModel.checkFavorite(id).observe(this@MovieDetailActivity, {
            if (it > 0) {
                isFavorite = true
                setStatusFavorite(isFavorite)
            } else {
                isFavorite = false
                setStatusFavorite(isFavorite)
            }
        })
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        with(detailMovieBinding) {
            if (statusFavorite) {
                favorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MovieDetailActivity,
                        R.drawable.ic_favorite
                    )
                )
                favorite.setOnClickListener {
                    movieDetailViewModel.deleteMovieDetail(movieDetail)
                    SnackBar.showSnackBar(
                        detailMovieBinding.root,
                        resources.getString(R.string.delete_favortie)
                    )
                }
            } else {
                favorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MovieDetailActivity,
                        R.drawable.ic_fav_border
                    )
                )
                favorite.setOnClickListener {
                    movieDetailViewModel.insertMovieDetail(movieDetail)
                    SnackBar.showSnackBar(
                        detailMovieBinding.root,
                        resources.getString(R.string.add_favorite)
                    )
                }

            }
        }
    }
}