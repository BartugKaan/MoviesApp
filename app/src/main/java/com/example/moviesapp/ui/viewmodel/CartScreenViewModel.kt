package com.example.moviesapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.data.entity.MovieCart
import com.example.moviesapp.data.repo.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(var cartRepository: CartRepository): ViewModel(){
    var cartMovieList = MutableLiveData<List<MovieCart>>()
    var userName = "BartugKaan"

    init {
        getAllCartMovies(userName)
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
                cartMovieList.postValue(emptyList())
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
            try {
                cartRepository.addMovieToCart(name, image, price, category, rating, year, director, description, orderAmount, userName)
                getAllCartMovies(userName)
            } catch (e: Exception) {
                Log.e("CartDebug", "Error adding to cart: ${e.message}")
            }
        }
    }

    fun deleteMovieFromCart(cartId:Int, userName: String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartRepository.deleteMovieFromCart(cartId = cartId, userName=userName)
                getAllCartMovies(userName)
            } catch (e: Exception) {
                Log.e("CartDebug", "Error deleting from cart: ${e.message}")
            }
        }
    }
}