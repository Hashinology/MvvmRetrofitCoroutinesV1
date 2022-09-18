package com.hashinology.mvvmretrofitcoroutinesv1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hashinology.mvvmretrofitcoroutinesv1.api.Repository
import com.hashinology.mvvmretrofitcoroutinesv1.model.Movie
import com.hashinology.mvvmretrofitcoroutinesv1.util.Resource
import java.io.IOException

class MovieViewModel(val repo: Repository): ViewModel() {
    private val _getMovie: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    var getMovie: LiveData<Resource<List<Movie>>> = _getMovie

    suspend fun getMovieViewList(){
        _getMovie.postValue(Resource.Loading())
        try {
            val response = repo.getResponseMovie()
            if (response.isSuccessful){
                val list = response.body()
                _getMovie.postValue(Resource.Success(list!!))
            }else{
                _getMovie.postValue(Resource.Error(response.message()))
            }

        }catch (t: Throwable){
            when(t){
                is IOException -> { _getMovie.postValue(Resource.Error("Network Failiure: "))}
                else -> {_getMovie.postValue(Resource.Error(t.message!!))}
            }
        }
    }
}