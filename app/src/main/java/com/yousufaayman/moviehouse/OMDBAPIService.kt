package com.yousufaayman.moviehouse

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDBApiService {
    @GET("/")
    fun getMovieInfo(
        @Query("apikey") apiKey: String,
        @Query("t") movieTitle: String
    ): Call<OMDBMovieModelClass>
}

object OMDBApiClient {
    private const val BASE_URL = "https://www.omdbapi.com/"

    fun create(): OMDBApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(OMDBApiService::class.java)
    }
}
