package com.hashinology.mvvmretrofitcoroutinesv1.api

class Repository {
    suspend fun getResponseMovie() = Retrofitclient.setInstance().getMovies()
}