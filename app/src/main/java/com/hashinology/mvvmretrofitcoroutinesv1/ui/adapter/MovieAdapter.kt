package com.hashinology.mvvmretrofitcoroutinesv1.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hashinology.mvvmretrofitcoroutinesv1.R
import com.hashinology.mvvmretrofitcoroutinesv1.model.Movie

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.ViewsHolder>() {
    val differCallBack = object: DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    class ViewsHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        val imgMovie = itemview.findViewById<ImageView>(R.id.ivMovie)
        val movieName = itemview.findViewById<TextView>(R.id.tvMovieName)
        val movieCategory = itemview.findViewById<TextView>(R.id.tvMovieCategory)
        val movieDec = itemview.findViewById<TextView>(R.id.tvMovieDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewsHolder {
        val views = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return ViewsHolder(views)
    }

    override fun onBindViewHolder(holder: ViewsHolder, position: Int) {
        val list = differ.currentList.get(position)
        Glide.with(holder.imgMovie.context)
            .load(list.imageUrl)
            .into(holder.imgMovie)
        holder.movieName.text = list.name
        holder.movieCategory.text = list.category
        holder.movieDec.text = list.desc
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}