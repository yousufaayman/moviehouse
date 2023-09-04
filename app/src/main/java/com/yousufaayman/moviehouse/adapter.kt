package com.yousufaayman.moviehouse
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class adapter : ListAdapter<movieModelClass, adapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val moviePosterImageView: ImageView = itemView.findViewById(R.id.imageView)
        private val movieTitleTextView: TextView = itemView.findViewById(R.id.nameTV)
        private val movieReleaseDateTextView: TextView = itemView.findViewById(R.id.releaseDate)

        val radius = itemView.context.resources.getDimensionPixelSize(R.dimen.corner_radius)
        fun bind(movie: movieModelClass) {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/original/${movie.poster_path}")
//                .transform(RoundedCorners(radius))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(moviePosterImageView)

            movieTitleTextView.text = movie.title
            movieReleaseDateTextView.text = "Release Date: ${movie.release_date}"

            }
        }

    }
private class MovieDiffCallback : DiffUtil.ItemCallback<movieModelClass>() {
        override fun areItemsTheSame(oldItem: movieModelClass, newItem: movieModelClass): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: movieModelClass, newItem: movieModelClass): Boolean {
            return oldItem == newItem
        }
    }




