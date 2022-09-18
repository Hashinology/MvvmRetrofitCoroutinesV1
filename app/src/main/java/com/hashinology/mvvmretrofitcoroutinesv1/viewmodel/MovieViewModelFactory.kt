package com.hashinology.mvvmretrofitcoroutinesv1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hashinology.mvvmretrofitcoroutinesv1.api.Repository

class MovieViewModelFactory (val repository: Repository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(repository) as T
    }
}