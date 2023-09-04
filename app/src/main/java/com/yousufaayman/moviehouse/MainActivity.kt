package com.yousufaayman.moviehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
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
        //~Setting Up Main Activity~
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //~Setting Adapter~
        movieRecyclerView = findViewById(R.id.movieRV)
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = adapter()
        movieRecyclerView.adapter = movieAdapter


        //~Extra Functionality~
        //Scroll Listener
        val scrollListener = ScrollListener(findViewById(R.id.bannerIV), this)
        movieRecyclerView.addOnScrollListener(scrollListener)

        //Add Dividers
        val customDivider = ContextCompat.getDrawable(this, R.drawable.custom_divider)
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        dividerItemDecoration.setDrawable(customDivider!!)

        movieRecyclerView.addItemDecoration(dividerItemDecoration)

        //Add padding between items
        val horizontalMarginInPixels = resources.getDimensionPixelSize(R.dimen.horizontal_margin)
        movieRecyclerView.setPadding(horizontalMarginInPixels, 0, horizontalMarginInPixels, 0)
        movieRecyclerView.clipToPadding = false


        //~API Call~
        val apiKey = "eafb61ad65af02dc924e6864218f9e88"
        val apiService = TMDBApiClient.create()

        apiService.getPopularMovies(apiKey, 1).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    val movies = movieResponse?.results ?: emptyList()
                    movieAdapter.submitList(movies)
                } else {
                    Log.e("API ERROR", "Couldn't Complete Request")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("API Request Failed", t.message ?: "Unknown error")
            }
        })


    }
}
