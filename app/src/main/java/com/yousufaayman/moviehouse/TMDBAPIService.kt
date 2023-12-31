package com.yousufaayman.moviehouse

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class MovieResponse(
    val results: List<TMDBMovieModelClass>
)
interface TMDBApiService {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRated(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<MovieResponse>
}
object TMDBApiClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    fun create(): TMDBApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TMDBApiService::class.java)
    }
}
