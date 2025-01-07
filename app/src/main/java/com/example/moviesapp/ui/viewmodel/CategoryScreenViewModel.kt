package com.example.moviesapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.data.entity.Movie
import com.example.moviesapp.data.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryScreenViewModel @Inject constructor(var movieRepository: MovieRepository) : ViewModel(){
    var movieList = MutableLiveData<List<Movie>>()

    init {
        getAllMovies()
    }

    fun getAllMovies(){
        CoroutineScope(Dispatchers.Main).launch {
            movieList.value = movieRepository.getAllMovies()
        }
    }
}