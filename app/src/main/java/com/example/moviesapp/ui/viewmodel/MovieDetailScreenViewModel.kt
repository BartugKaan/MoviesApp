package com.example.moviesapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.data.entity.Movie
import com.example.moviesapp.data.entity.MovieCart
import com.example.moviesapp.data.repo.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailScreenViewModel @Inject constructor(var cartRepository: CartRepository): ViewModel() {
    var cartMovieList = MutableLiveData<List<MovieCart>>()
    var userName = "BartugKaan"

    init {
        getAllCartMovies(userName)
    }

    fun printCountOfCartMovieList(){
        getAllCartMovies(userName)
        Log.e("cartMovieList", "cartMovieList count = ${cartMovieList.value?.count()}")
        Log.e("cartMovieList", "cartMovieList count = ${cartMovieList.value?.get(0)?.name}")
    }

    fun getAllCartMovies(userName: String){
        Log.e("CartDebug", "getAllCartMovies called with username: $userName")
        CoroutineScope(Dispatchers.Main).launch{
            try {
                Log.e("CartDebug", "Making API call...")
                cartMovieList.value = cartRepository.getMoviesFromCart(userName)
                Log.e("CartDebug", "API call successful, items: ${cartMovieList.value?.size}")
            } catch (e: Exception) {
                Log.e("CartDebug", "Error in getAllCartMovies: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun addMovieToCart(name:String,
                       image: String,
                       price:Int,
                       category:String,
                       rating: Double,
                       year: Int,
                       director:String,
                       description: String,
                       orderAmount: Int,
                       userName:String
                       ){
        CoroutineScope(Dispatchers.Main).launch {
            cartRepository.addMovieToCart(name, image, price, category, rating, year, director, description, orderAmount, userName)
        }
    }

}