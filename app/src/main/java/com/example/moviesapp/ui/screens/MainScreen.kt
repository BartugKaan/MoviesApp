package com.example.moviesapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviesapp.data.entity.Movie
import com.example.moviesapp.ui.components.CustomCarousel
import com.example.moviesapp.ui.viewmodel.MainScreenViewModel
import androidx.compose.material3.CircularProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel) {
    val movieList = mainScreenViewModel.movieList.observeAsState(listOf())

    Scaffold(
        topBar = { 
            CenterAlignedTopAppBar(
                title = { Text("Popcorn Deals") }
            ) 
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (movieList.value.isEmpty()) {
                CircularProgressIndicator()
            } else {
                CustomCarousel(movieList = movieList.value)
            }
            Text("Deneme")
            Text("Deneme")
            Text("Deneme")
            Text("Deneme")
            Text("Deneme")
        }
    }
}