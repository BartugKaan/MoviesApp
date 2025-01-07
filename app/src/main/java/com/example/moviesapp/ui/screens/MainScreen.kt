package com.example.moviesapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviesapp.data.entity.Movie
import com.example.moviesapp.ui.components.CarouselSlider
import com.example.moviesapp.ui.components.CategoryButton
import com.example.moviesapp.ui.components.MovieCard
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
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (movieList.value.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {  }
            CircularProgressIndicator()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item { CategoryButton("🎬All") { } }
                        item { CategoryButton("⚔️ Action") { } }
                        item { CategoryButton("🎭 Drama") { } }
                        item { CategoryButton("👽 Sci-Fi") { } }
                        item { CategoryButton("🐉 Fantastic") { } }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Top 5 Popular Movies
                    Text(
                        text = "Top 5 Popular Movies",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    CarouselSlider(movies = movieList.value, navController)
                }
                
                item {
                    Text(
                        text = "Latest Movies",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Sorted Movies by Year
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(movieList.value.sortedByDescending { it.year }) { movie ->
                            MovieCard(
                                movie = movie,
                                navController = navController,
                                modifier = Modifier.width(200.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
