package com.hashinology.mvvmretrofitcoroutinesv1.api

import com.hashinology.mvvmretrofitcoroutinesv1.model.Movie
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Retrofitclient {
    @GET("movielist.json")
    suspend fun getMovies(): Response<List<Movie>>

    companion object{
        var instance: Retrofitclient? = null
        fun setInstance(): Retrofitclient{
            if (instance == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://howtodoandroid.com/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
                instance = retrofit.create(Retrofitclient::class.java)
            }
            return instance!!
        }
    }
}