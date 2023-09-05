package com.yousufaayman.moviehouse

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecycleViewAdaptor(private val fragmentManager: FragmentManager, private val dContext : Context) : ListAdapter<TMDBMovieModelClass, RecycleViewAdaptor.MovieViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, fragmentManager, dContext)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val moviePosterImageView: ImageView = itemView.findViewById(R.id.imageView)
        private val movieTitleTextView: TextView = itemView.findViewById(R.id.nameTV)
        private val movieReleaseDateTextView: TextView = itemView.findViewById(R.id.releaseDate)
        private val getMoreInfo: Button = itemView.findViewById(R.id.buttonGetInfo)
        fun bind(movie: TMDBMovieModelClass, fragmentManager: FragmentManager, dContext: Context) {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/original/${movie.poster_path}")
                .transition(DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()))
                .into(moviePosterImageView)

            movieTitleTextView.text = movie.title
            movieReleaseDateTextView.text = "Release Date: ${movie.release_date}"

            getMoreInfo.setOnClickListener {
                OMDBApiCall(movie.title, fragmentManager, dContext)
            }

            }

        private fun OMDBApiCall(movieTitle: String, fragmentManager: FragmentManager, dContext: Context) {
            val apiKey = "f02289ca"
            val apiService = OMDBApiClient.create()
            apiService.getMovieInfo(apiKey, movieTitle).enqueue(object : Callback<OMDBMovieModelClass> {
                override fun onResponse(
                    call: Call<OMDBMovieModelClass>,
                    response: Response<OMDBMovieModelClass>
                ) {
                    if (response.isSuccessful) {
                        val movie = response.body()
                        Log.e("MOVIE-NAME", "testcase ${movie?.Title}")
                        if (movie?.Title == null) {
                            Toast.makeText(
                                dContext,
                                "API ERROR: Sorry Cannot Find Movie Information",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            movie?.let {
                                val dialogFragment = OMDBMovieInfoDialogFragment()
                                val bundle = Bundle()
                                bundle.putParcelable("movie", it)
                                dialogFragment.arguments = bundle
                                dialogFragment.show(fragmentManager, "movieInfoDialog")
                            }
                        }
                    } else {
                        Toast.makeText(
                            dContext,
                            "API ERROR: Sorry Cannot Find Movie Information",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(call: Call<OMDBMovieModelClass>, t: Throwable) {
                    Toast.makeText(
                        dContext,
                        "API ERROR: Please Restart Application and Check Internet Connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

    }
}

private class MovieDiffCallback : DiffUtil.ItemCallback<TMDBMovieModelClass>() {
        override fun areItemsTheSame(oldItem: TMDBMovieModelClass, newItem: TMDBMovieModelClass): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TMDBMovieModelClass, newItem: TMDBMovieModelClass): Boolean {
            return oldItem == newItem
        }
    }




