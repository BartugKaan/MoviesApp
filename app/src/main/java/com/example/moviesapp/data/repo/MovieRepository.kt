package com.example.moviesapp.data.repo

import com.example.moviesapp.data.datasource.MovieDataSource
import com.example.moviesapp.data.entity.Movie

class MovieRepository(var movieDataSource: MovieDataSource) {
    suspend fun getAllMovies() : List<Movie> = movieDataSource.getAllMovies()

}