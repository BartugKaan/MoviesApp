package com.example.moviesapp.data.datasource

import com.example.moviesapp.retrofit.CartDao

class CartDataSource(var cartDao: CartDao) {
    suspend fun getMoviesFromCart(userName:String){
        cartDao.getMoviesFromCart(userName)
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