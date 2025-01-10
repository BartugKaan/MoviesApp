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
    var userName = MutableLiveData<String>()

    fun setUserName(name: String) {
        userName.value = name
        getAllCartMovies()
    }

    private fun getAllCartMovies() {
        val currentUserName = userName.value ?: return
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartMovieList.value = cartRepository.getMoviesFromCart(currentUserName)
            } catch (e: Exception) {
                Log.e("CartError", "Error loading cart: ${e.message}")
                cartMovieList.value = emptyList()
            }
        }
    }

    fun addToCart(
        name: String, image: String, price: Int, category: String,
        rating: Double, year: Int, director: String, description: String,
        orderAmount: Int
    ) {
        val currentUserName = userName.value ?: return

        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartRepository.addMovieToCart(
                    name = name,
                    image = image,
                    price = price,
                    category = category,
                    rating = rating,
                    year = year,
                    director = director,
                    description = description,
                    orderAmount = orderAmount,
                    userName = currentUserName
                )
            } catch (e: Exception) {
                Log.e("CartError", "Error adding to cart: ${e.message}")
            }
        }
    }
}