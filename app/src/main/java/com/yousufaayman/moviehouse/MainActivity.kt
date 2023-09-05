package com.yousufaayman.moviehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var movieRecycleViewAdaptor: RecycleViewAdaptor
    private lateinit var movieRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        //~Setting Up Main Activity~
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting Adapter
        movieRecyclerView = findViewById(R.id.movieRV)
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        movieRecycleViewAdaptor = RecycleViewAdaptor(supportFragmentManager, this)
        movieRecyclerView.adapter = movieRecycleViewAdaptor
        val getPopular: Button = findViewById(R.id.buttonTredning)
        val getTopRated: Button = findViewById(R.id.buttonTopRated)
        val fabScrollToTop: FloatingActionButton = findViewById(R.id.fabScrollToTop)


        //Adding Styling to Recycler View
        recycleViewStyle(movieRecyclerView, fabScrollToTop)

        //Default API Call
        apiCall(movieRecycleViewAdaptor, 1)

        //Button Listeners to switch recyclerview info
        getPopular.setOnClickListener {
            apiCall(movieRecycleViewAdaptor, 1)
        }
        getTopRated.setOnClickListener {
            apiCall(movieRecycleViewAdaptor, 2)
        }

        //Button Listeners to return to top of screen
        fabScrollToTop.setOnClickListener {
            // Replace 'recyclerView' with your RecyclerView instance
            movieRecyclerView.smoothScrollToPosition(0)
        }
    }
    private fun recycleViewStyle(targetRecycleView: RecyclerView, fabScrollToTop: FloatingActionButton){
        val scrollListener = ScrollListener(findViewById(R.id.bannerIV), this, fabScrollToTop)
        targetRecycleView.addOnScrollListener(scrollListener)

        //Add Dividers
        val customDivider = ContextCompat.getDrawable(this, R.drawable.custom_divider)
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        dividerItemDecoration.setDrawable(customDivider!!)

        targetRecycleView.addItemDecoration(dividerItemDecoration)

        //Add padding between items
        val horizontalMarginInPixels = resources.getDimensionPixelSize(R.dimen.horizontal_margin)
        targetRecycleView.setPadding(horizontalMarginInPixels, 0, horizontalMarginInPixels, 0)
        targetRecycleView.clipToPadding = false

    }
    private fun apiCall(RecycleViewAdapt: RecycleViewAdaptor, searchMode: Int){
        val apiKey = "eafb61ad65af02dc924e6864218f9e88"
        val apiService = TMDBApiClient.create()
        when (searchMode) {
            1 -> {
                apiService.getPopularMovies(apiKey, 1).enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                        if (response.isSuccessful) {
                            val movieResponse = response.body()
                            val movies = movieResponse?.results ?: emptyList()
                            RecycleViewAdapt.submitList(movies)
                        } else {
                            Log.e("API ERROR", "Couldn't Complete Request")
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.e("API Request Failed", t.message ?: "Unknown error")
                    }
                })
            }
            2 -> {
                apiService.getTopRated(apiKey, 1).enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                        if (response.isSuccessful) {
                            val movieResponse = response.body()
                            val movies = movieResponse?.results ?: emptyList()
                            RecycleViewAdapt.submitList(movies)
                        } else {
                            Log.e("API ERROR", "Couldn't Complete Request")
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.e("API Request Failed", t.message ?: "Unknown error")
                    }
                })
            }
            else -> {
                Toast.makeText(
                    this,
                    "Error Loading Movies. Please Restart Application",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
