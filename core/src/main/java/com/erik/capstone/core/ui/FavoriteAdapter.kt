package com.erik.capstone.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erik.capstone.core.BuildConfig
import com.erik.capstone.core.R
import com.erik.capstone.core.databinding.ItemsFavoriteBinding
import com.erik.capstone.core.domain.model.MovieDetail
import com.erik.capstone.core.utils.loadImage

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    private var listData = ArrayList<MovieDetail>()
    var onItemClick: ((MovieDetail) -> Unit)? = null

    fun setData(newListData: List<MovieDetail>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_favorite, parent, false)
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemsFavoriteBinding.bind(itemView)
        fun bind(data: MovieDetail) {
            with(binding) {
                imgPoster.loadImage(BuildConfig.BASE_URL_POSTER_PATH_BIG + data.backdropPath)
                tvTitle.text = data.title
                tvRelease.text = StringBuilder("Release Date ${data.releaseDate}")
                tvScore.text = data.voteAverage.toString()
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}