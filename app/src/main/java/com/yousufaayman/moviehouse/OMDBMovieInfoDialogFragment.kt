package com.yousufaayman.moviehouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

class OMDBMovieInfoDialogFragment : DialogFragment() {
    companion object {
        fun newInstance(movie: OMDBMovieModelClass): OMDBMovieInfoDialogFragment {
            val fragment = OMDBMovieInfoDialogFragment()
            val args = Bundle()
            args.putParcelable("movie", movie)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_movie_info, container, false)
        // Initialize views and populate them with movie data
        val dialogTitleTV = view.findViewById<TextView>(R.id.movieTitleDialogTv)
        val dialogDirectorTV = view.findViewById<TextView>(R.id.movieDirectorTV)
        val dialogRatingTV = view.findViewById<TextView>(R.id.movieRatingTV)
        val dialogDateTV = view.findViewById<TextView>(R.id.dialogReleaseDateTV)
        val dialogGenreTV = view.findViewById<TextView>(R.id.movieGenreTV)
        val dialogRTimeTV = view.findViewById<TextView>(R.id.movieRTimeTV)
        val dialogActorsTV = view.findViewById<TextView>(R.id.movieActorsTV)
        val dialogPlotV = view.findViewById<TextView>(R.id.moviePlotTv)
        val dialogPosterIV = view.findViewById<ImageView>(R.id.moviePoster)
        val dialogRatedWTV = view.findViewById<TextView>(R.id.movieRatedTv)

        // Extract movie data from arguments and set it in the views
        val movie = arguments?.getParcelable<OMDBMovieModelClass>("movie")
        dialogTitleTV.text = movie?.Title
        dialogDirectorTV.text = "Director: ${movie?.Director}"
        dialogRatingTV.text = "Metascore: ${movie?.Metascore}"
        dialogDateTV.text = "Released: ${movie?.Released}"
        dialogGenreTV.text = "Genre: ${movie?.Genre}"
        dialogRTimeTV.text = "Runtime: ${movie?.Runtime}"
        dialogActorsTV.text = movie?.Actors
        dialogPlotV.text = movie?.Plot
        dialogRatedWTV.text = "Rated: ${movie?.Rated}"
        Glide.with(view.context)
            .load(movie?.Poster)
            .transition(DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()))
            .into(dialogPosterIV)

        return view
    }
}