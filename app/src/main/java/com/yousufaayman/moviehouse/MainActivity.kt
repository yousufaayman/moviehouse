package com.yousufaayman.moviehouse

import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var movieAdapter: adapter
    private lateinit var movieRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieRecyclerView = findViewById(R.id.movieRV)
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = adapter()

        movieRecyclerView.adapter = movieAdapter

        // Add this code after setting up the adapter
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        dividerItemDecoration.setDrawable(
            ResourcesCompat.getDrawable(resources, android.R.color.white, theme)!!)
        val horizontalMarginInPixels = resources.getDimensionPixelSize(R.dimen.horizontal_margin)

// Add horizontal dividers
        movieRecyclerView.addItemDecoration(dividerItemDecoration)

// Add padding between items
        movieRecyclerView.setPadding(horizontalMarginInPixels, 0, horizontalMarginInPixels, 0)
        movieRecyclerView.clipToPadding = false

        val apiKey = "eafb61ad65af02dc924e6864218f9e88"
        val apiService = TMDBApiClient.create()

        apiService.getPopularMovies(apiKey, 1).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    val movies = movieResponse?.results ?: emptyList()
                    movieAdapter.submitList(movies)
                } else {
                    // Handle API error here (e.g., display an error message)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("API Request Failed", t.message ?: "Unknown error")
            }
        })
    }
}