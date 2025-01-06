package com.example.moviesapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesapp.data.entity.Movie
import com.example.moviesapp.ui.viewmodel.MovieDetailScreenViewModel
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun MovieDetail(movie: Movie, movieDetailScreenViewModel: MovieDetailScreenViewModel){
    val baseUrl = "http://kasimadalan.pe.hu/movies/images/"

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(imageModel = baseUrl + movie.image, modifier = Modifier.size(200.dp, 300.dp))
        Text("Fiyat : ${movie.price}", fontSize = 50.sp)
        Button(onClick = {
            movieDetailScreenViewModel.addToCart(
                name = movie.name,
                image = movie.image,
                price = movie.price,
                category = movie.category,
                rating = movie.rating,
                year = movie.year,
                director = movie.director,
                description = movie.description
            )
        }) {
            Text("Add Cart")
        }
    }
}