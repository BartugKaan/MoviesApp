package com.example.moviesapp.data.datasource

import com.example.moviesapp.data.entity.Movie
import com.example.moviesapp.retrofit.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDataSource(var movieDao: MovieDao) {
    suspend fun getAllMovies() : List<Movie> = withContext(Dispatchers.IO){
        return@withContext movieDao.getAllMovies().movies
    }
}