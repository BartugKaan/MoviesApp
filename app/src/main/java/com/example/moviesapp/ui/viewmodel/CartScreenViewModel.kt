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
class CartScreenViewModel @Inject constructor(var cartRepository: CartRepository): ViewModel() {
    // LiveData for  cart items, userName and total amount in the UI
    var cartMovieList = MutableLiveData<List<MovieCart>>()
    var totalAmount = MutableLiveData<Int>(0)
    var userName = MutableLiveData<String>()

    init {
        loadCart()
    }


    // Loads cart data from repository and combines same movies - ?
    fun loadCart() {
        if (userName.value.isNullOrEmpty()) return
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartMovieList.value = cartRepository.getMoviesFromCart(userName.value!!)
                calculateTotal()
            } catch (e: Exception) {
                Log.e("CartError", "Error loading cart: ${e.message}")
                cartMovieList.value = emptyList()
            }
        }
    }

    private fun calculateTotal() {
        totalAmount.value = cartMovieList.value?.sumOf { it.price * it.orderAmount } ?: 0
    }

    fun setUserName(name: String) {
        userName.value = name
        loadCart()
    }

    // Updates quantity of a movie in cart, removes if quantity <= 0
    fun updateQuantity(cartId: Int, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartId)
            return
        }

        // Live data null check
        val movie = cartMovieList.value?.find { it.cartId == cartId } ?: return
        val currentUserName = userName.value ?: return
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val allMovies = cartRepository.getMoviesFromCart(currentUserName)
                allMovies.filter { it.name == movie.name }.forEach {
                    cartRepository.deleteMovieFromCart(it.cartId, currentUserName)
                }
                
                cartRepository.addMovieToCart(
                    name = movie.name,
                    image = movie.image,
                    price = movie.price,
                    category = movie.category,
                    rating = movie.rating,
                    year = movie.year,
                    director = movie.director,
                    description = movie.description,
                    orderAmount = newQuantity,
                    userName = currentUserName
                )
                loadCart()
            } catch (e: Exception) {
                Log.e("CartError", "Error updating quantity: ${e.message}")
            }
        }
    }

    // Removes all movies of a movie from cart
    fun removeFromCart(cartId: Int) {
        val movieToRemove = cartMovieList.value?.find { it.cartId == cartId } ?: return
        val currentUserName = userName.value ?: return
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                //Deletes that movie with all quantities
                val allMovies = cartRepository.getMoviesFromCart(currentUserName)
                allMovies.filter { it.name == movieToRemove.name }.forEach {
                    cartRepository.deleteMovieFromCart(it.cartId, currentUserName)
                }
                loadCart()
            } catch (e: Exception) {
                Log.e("CartError", "Error removing item: ${e.message}")
            }
        }
    }

    // Clears all items from cart
    fun clearCart() {
        val currentUserName = userName.value ?: return
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val currentMovies = cartMovieList.value ?: return@launch
                for (movie in currentMovies) {
                    val allMovies = cartRepository.getMoviesFromCart(currentUserName)
                    allMovies.filter { it.name == movie.name }.forEach {
                        cartRepository.deleteMovieFromCart(it.cartId, currentUserName)
                    }
                }
                cartMovieList.value = emptyList()
                totalAmount.value = 0
            } catch (e: Exception) {
                Log.e("CartError", "Error clearing cart: ${e.message}")
            }
        }
    }

    // Adds movie to cart, combining with existing entries if any
    fun addToCart(name: String, image: String, price: Int, category: String,
                 rating: Double, year: Int, director: String, description: String) {
        val currentUserName = userName.value ?: return
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val allMovies = cartRepository.getMoviesFromCart(currentUserName)
                val existingMovies = allMovies.filter { it.name == name }
                val currentQuantity = existingMovies.sumOf { it.orderAmount }

                existingMovies.forEach {
                    cartRepository.deleteMovieFromCart(it.cartId, currentUserName)
                }

                cartRepository.addMovieToCart(
                    name = name,
                    image = image,
                    price = price,
                    category = category,
                    rating = rating,
                    year = year,
                    director = director,
                    description = description,
                    orderAmount = currentQuantity + 1,
                    userName = currentUserName
                )
                loadCart()
            } catch (e: Exception) {
                Log.e("CartError", "Error adding to cart: ${e.message}")
            }
        }
    }
}