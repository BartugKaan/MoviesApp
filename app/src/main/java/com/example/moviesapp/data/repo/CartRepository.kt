package com.example.moviesapp.data.repo

import android.util.Log
import com.example.moviesapp.data.datasource.CartDataSource
import com.example.moviesapp.data.entity.MovieCart

class CartRepository(var cartDataSource: CartDataSource) {
    suspend fun getMoviesFromCart(userName:String): List<MovieCart> {
        Log.e("CartDebug", "Repository: getMoviesFromCart called")
        return try {
            val result = cartDataSource.getAllMoviesFromCart(userName)
            Log.e("CartDebug", "Repository: got result with size: ${result.size}")
            result
        } catch (e: Exception) {
            Log.e("CartDebug", "Repository: Error: ${e.message}")
            throw e
        }
    }
    suspend fun deleteMovieFromCart(cartId: Int, userName: String) = cartDataSource.deleteMovieFromCart(cartId, userName)
    suspend fun addMovieToCart(name:String,
                               image:String,
                               price:Int,
                               category: String,
                               rating:Double,
                               year:Int,
                               director:String,
                               description:String,
                               orderAmount:Int,
                               userName:String) = cartDataSource.addMovieToCart(name, image, price, category, rating, year, director, description, orderAmount, userName)
}