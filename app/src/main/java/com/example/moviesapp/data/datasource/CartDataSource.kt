package com.example.moviesapp.data.datasource

import android.util.Log
import com.example.moviesapp.data.entity.Movie
import com.example.moviesapp.data.entity.MovieCart
import com.example.moviesapp.retrofit.CartDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartDataSource(var cartDao: CartDao) {

    suspend fun getAllMoviesFromCart(userName: String): List<MovieCart> = withContext(Dispatchers.IO){
        Log.e("CartDebug", "DataSource: Making API call for user: $userName")
        try {
            val response = cartDao.getMoviesFromCart(userName)
            Log.e("CartDebug", "DataSource: response received")
            return@withContext response.movie_cart
        } catch (e: Exception) {
            Log.e("CartDebug", "DataSource: Error in API call: ${e.message}")
            throw e
        }
    }

    suspend fun addMovieToCart(name:String,
                               image:String,
                               price:Int,
                               category: String,
                               rating:Double,
                               year:Int,
                               director:String,
                               description:String,
                               orderAmount:Int,
                               userName:String){
        cartDao.addMovieToCart(name,image,price,category,rating,year,director,description,orderAmount,userName)
    }

    suspend fun deleteMovieFromCart(cartId:Int, userName: String){
        cartDao.deleteMovieFromCart(cartId,userName)
    }
}