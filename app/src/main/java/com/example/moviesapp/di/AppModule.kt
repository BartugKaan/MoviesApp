package com.example.moviesapp.di

import com.example.moviesapp.data.datasource.CartDataSource
import com.example.moviesapp.data.datasource.MovieDataSource
import com.example.moviesapp.data.repo.CartRepository
import com.example.moviesapp.data.repo.MovieRepository
import com.example.moviesapp.retrofit.ApiUtils
import com.example.moviesapp.retrofit.CartDao
import com.example.moviesapp.retrofit.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideCartRepository(cartDataSource: CartDataSource) : CartRepository{
        return CartRepository(cartDataSource)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieDataSource: MovieDataSource) : MovieRepository{
        return MovieRepository(movieDataSource)
    }

    @Provides
    @Singleton
    fun provideCartDataSource(cartDao: CartDao) : CartDataSource{
        return  CartDataSource(cartDao)
    }

    @Provides
    @Singleton
    fun provideMovieDataSource(movieDao: MovieDao) : MovieDataSource{
        return  MovieDataSource(movieDao)
    }

    @Provides
    @Singleton
    fun provideMovieDao() : MovieDao{
        return ApiUtils.getMovieDao()
    }

    @Provides
    @Singleton
    fun provideCartDao() : CartDao{
        return ApiUtils.getCartDao()
    }
}