package com.hashinology.mvvmretrofitcoroutinesv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hashinology.mvvmretrofitcoroutinesv1.api.Repository
import com.hashinology.mvvmretrofitcoroutinesv1.databinding.ActivityMainBinding
import com.hashinology.mvvmretrofitcoroutinesv1.ui.adapter.MovieAdapter
import com.hashinology.mvvmretrofitcoroutinesv1.util.Resource
import com.hashinology.mvvmretrofitcoroutinesv1.viewmodel.MovieViewModel
import com.hashinology.mvvmretrofitcoroutinesv1.viewmodel.MovieViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var movieViewModel: MovieViewModel
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        getViewModelSetup()

        setRecyclerView()

        lifecycleScope.launch(Dispatchers.IO){
            movieViewModel.getMovieViewList()
        }
        movieViewModel.getMovie.observe(this, Observer { resource ->
            when(resource){
                is Resource.Loading -> {
                    showProgressBar()
                    hideErrorMessage()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    resource.data.let {
                        movieAdapter.differ.submitList(it)
                    }
//                   movieAdapter.differ.submitList(resource.data)
                }
                is Resource.Error -> {
                    hideProgressBar()
                    showErrorMessage(resource.message)
                }
            }
        })
    }

    private fun showErrorMessage(message: String?) {
        binding.tvErrorMsg.visibility = View.VISIBLE
        binding.tvErrorMsg.text = message
    }

    private fun hideProgressBar() {
        binding.pbLoading.visibility = View.INVISIBLE
    }

    private fun hideErrorMessage() {
        binding.tvErrorMsg.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun getViewModelSetup() {
        val repo = Repository()
        val factory = MovieViewModelFactory(repo)

        movieViewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)
    }

    private fun setRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.rvMovieList.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }
}