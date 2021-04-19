package com.erik.capstone.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erik.capstone.core.BuildConfig
import com.erik.capstone.core.R
import com.erik.capstone.core.databinding.ItemsViewholderBinding
import com.erik.capstone.core.domain.model.Movie
import com.erik.capstone.core.utils.loadImage
import java.util.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_viewholder, parent, false)
        )

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemsViewholderBinding.bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                imgPoster.loadImage(BuildConfig.BASE_URL_POSTER_PATH_BIG + data.poster_path)
                tvItemTitle.text = data.movieTitle
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}