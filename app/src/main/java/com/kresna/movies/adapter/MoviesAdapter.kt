package com.kresna.movies.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kresna.movies.R
import com.kresna.movies.model.ResultsItem
import com.kresna.movies.view.detail.DetailActivity
import com.squareup.picasso.Picasso

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPoster = itemView.findViewById<ImageView>(R.id.iv_poster)
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val tvDate = itemView.findViewById<TextView>(R.id.tv_date)
    }

    private val differCallback = object : DiffUtil.ItemCallback<ResultsItem>() {
        override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = differ.currentList[position]

        holder.itemView.apply {
            Picasso.get().load("https://api.themoviedb.org" + movie.posterPath)
                .into(holder.ivPoster)
            holder.tvTitle.text = movie.title
            holder.tvDate.text = movie.releaseDate
            setOnClickListener {
                Intent(context, DetailActivity::class.java).apply {
                    putExtra("title", movie.title)
                    putExtra("overview", movie.overview)
                    putExtra("date", movie.releaseDate)
                    putExtra("image", movie.posterPath)
                }.also { context.startActivity(it) }
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((ResultsItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResultsItem) -> Unit) {
        onItemClickListener = listener
    }
}