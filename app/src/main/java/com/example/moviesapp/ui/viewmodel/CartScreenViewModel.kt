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
    // LiveData to observe cart items and total amount in the UI
    var cartMovieList = MutableLiveData<List<MovieCart>>()
    var totalAmount = MutableLiveData<Int>(0)
    private val userName = "BartugKaanTest"

    init {
        loadCart()
    }

    // Combines multiple entries of the same movie into a single entry with summed quantities
    private fun consolidateMovies(movies: List<MovieCart>): List<MovieCart> {
        return movies.groupBy { it.name }
            .map { (_, sameMovies) ->
                sameMovies.first().copy(
                    orderAmount = sameMovies.sumOf { it.orderAmount }
                )
            }
    }

    // Loads cart data from repository and combines same movies
    fun loadCart() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val movies = cartRepository.getMoviesFromCart(userName)
                cartMovieList.value = consolidateMovies(movies)
                updateTotalAmount()
            } catch (e: Exception) {
                Log.e("CartError", "Error loading cart: ${e.message}")
                cartMovieList.value = emptyList()
                totalAmount.value = 0
            }
        }
    }

    // Updates the total amount based on current cart items
    private fun updateTotalAmount() {
        val total = cartMovieList.value?.sumOf { it.price * it.orderAmount } ?: 0
        totalAmount.value = total
    }

    // Updates quantity of a movie in cart, removes if quantity <= 0
    fun updateQuantity(cartId: Int, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartId)
            return
        }

        val movie = cartMovieList.value?.find { it.cartId == cartId } ?: return
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // First delete all entries of this movie
                val allMovies = cartRepository.getMoviesFromCart(userName)
                allMovies.filter { it.name == movie.name }.forEach {
                    cartRepository.deleteMovieFromCart(it.cartId, userName)
                }
                
                // Add single entry with new quantity
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
                    userName = userName
                )
                loadCart()
            } catch (e: Exception) {
                Log.e("CartError", "Error updating quantity: ${e.message}")
            }
        }
    }

    // Removes all entries of a movie from cart
    fun removeFromCart(cartId: Int) {
        val movieToRemove = cartMovieList.value?.find { it.cartId == cartId } ?: return
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                //Deletes that movie with all quantities
                val allMovies = cartRepository.getMoviesFromCart(userName)
                allMovies.filter { it.name == movieToRemove.name }.forEach {
                    cartRepository.deleteMovieFromCart(it.cartId, userName)
                }
                loadCart()
            } catch (e: Exception) {
                Log.e("CartError", "Error removing item: ${e.message}")
            }
        }
    }

    // Clears all items from cart
    fun clearCart() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val currentMovies = cartMovieList.value ?: return@launch
                for (movie in currentMovies) {
                    val allMovies = cartRepository.getMoviesFromCart(userName)
                    allMovies.filter { it.name == movie.name }.forEach {
                        cartRepository.deleteMovieFromCart(it.cartId, userName)
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
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Checks all movie records
                val allMovies = cartRepository.getMoviesFromCart(userName)
                val existingMovies = allMovies.filter { it.name == name }
                val currentQuantity = existingMovies.sumOf { it.orderAmount }


                // Deletes each movies from existingMovies
                existingMovies.forEach {
                    cartRepository.deleteMovieFromCart(it.cartId, userName)
                }

                //Adds movie with new quantity
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
                    userName = userName
                )
                loadCart()
            } catch (e: Exception) {
                Log.e("CartError", "Error adding to cart: ${e.message}")
            }
        }
    }
}