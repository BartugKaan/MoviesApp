package com.example.moviesapp.data.repo

import com.example.moviesapp.data.datasource.CartDataSource

class CartRepository(var cartDataSource: CartDataSource) {
    suspend fun getMoviesFromCart(userName:String) = cartDataSource.getMoviesFromCart(userName)
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