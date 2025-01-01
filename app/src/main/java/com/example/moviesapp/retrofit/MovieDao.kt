package com.example.moviesapp.retrofit

import com.example.moviesapp.data.entity.MoviesResponse
import retrofit2.http.GET

interface MovieDao {
    //Dao - Database access Object
    //get all movies - http://kasimadalan.pe.hu/movies/getAllMovies.php
    //http://kasimadalan.pe.hu/ -> Base URL
    ///movies/getAllMovies.php -> Api Url

    //For listing all movies
    @GET("movies/getAllMovies.php")
    suspend fun getAllMovies() : MoviesResponse

}