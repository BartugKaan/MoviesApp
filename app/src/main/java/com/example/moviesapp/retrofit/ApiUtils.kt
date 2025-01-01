package com.example.moviesapp.retrofit

class ApiUtils {
    companion object{
        var BASE_URL = "http://kasimadalan.pe.hu/"

        fun getMovieDao() : MovieDao{
            return RetrofitClient.getClient(BASE_URL).create(MovieDao::class.java)
        }

        fun getCartDao() : CartDao{
            return RetrofitClient.getClient(BASE_URL).create(CartDao::class.java)
        }
    }
}