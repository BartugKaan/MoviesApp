package com.example.moviesapp.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviesapp.ui.components.CarouselSlider
import com.example.moviesapp.ui.components.CustomBottomAppBar
import com.example.moviesapp.ui.viewmodel.MainScreenViewModel
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel, 
    navController: NavController,
    paddingValues: PaddingValues
) {
    val movieList = mainScreenViewModel.movieList.observeAsState(listOf())
    val baseUrl = "http://kasimadalan.pe.hu/movies/images/"

    LaunchedEffect(key1 = true) {
        mainScreenViewModel.getAllMovies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (movieList.value.isEmpty()) {
            CircularProgressIndicator()
        } else {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                CarouselSlider(movies = movieList.value, navController)
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(count = 2)
                ) {
                    items(
                        //TODO:Custom Movie Cart needed
                        count = movieList.value.count(),
                        itemContent = {
                            var movie = movieList.value[it]
                            Log.e("Movie", "Movie name ${movie.name}")
                            Card(modifier = Modifier.padding(all = 5.dp)) {
                                Column(
                                    modifier = Modifier.fillMaxWidth().clickable {
                                        val movieJson = Gson().toJson(movie)
                                        navController.navigate(route = "movieDetailScreen/$movieJson")
                                    }
                                ) {
                                    GlideImage(imageModel = baseUrl + movie.image, modifier = Modifier.size(200.dp,300.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("${movie.name}", fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}