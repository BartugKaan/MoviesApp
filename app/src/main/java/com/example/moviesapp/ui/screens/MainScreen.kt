package com.example.moviesapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviesapp.ui.components.CarouselSlider
import com.example.moviesapp.ui.components.CategoryButton
import com.example.moviesapp.ui.components.LottieAnimation
import com.example.moviesapp.ui.components.LottieAnimationSmall
import com.example.moviesapp.ui.components.MovieCard
import com.example.moviesapp.ui.viewmodel.MainScreenViewModel
import com.google.gson.Gson

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel, 
    navController: NavController,
    paddingValues: PaddingValues
) {
    val movieList = mainScreenViewModel.movieList.observeAsState(listOf())

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
            LottieAnimation("https://lottie.host/acb05c5e-96f0-4c9c-b816-d4b922aa4344/3J8DGFR3Iv.lottie")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item { CategoryButton("🎬All") {
                            navController.navigate("categoryScreen/All")
                        } }
                        item { CategoryButton("⚔️ Action") {
                            navController.navigate("categoryScreen/Action")
                        } }
                        item { CategoryButton("🎭 Drama") {
                            navController.navigate("categoryScreen/Drama")
                        } }
                        item { CategoryButton("👽 Sci-Fi") {
                            navController.navigate("categoryScreen/Science Fiction")
                        } }
                        item { CategoryButton("🐉 Fantastic") {
                            navController.navigate("categoryScreen/Fantastic")
                        } }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Displays Top 5 Popular Movies
                    Text(
                        text = "Top 5 Popular Movies",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    CarouselSlider(movies = movieList.value, navController)
                }
                //Displays Newest movies to oldest
                item {
                    Text(
                        text = "Newest Movies to Oldest",
                        style = MaterialTheme.typography.titleMedium,
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
                    Spacer(modifier = Modifier.height(4.dp))
                }
                //Displays Christopher Nolan's movies
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Best Of Christopher Nolan",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val nolanMovies = movieList.value
                        .filter { it.director == "Christopher Nolan" }
                        .sortedByDescending { it.rating }
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(nolanMovies) { movie ->
                            MovieCard(
                                movie = movie,
                                navController = navController,
                                modifier = Modifier.width(200.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                // Random Movie Generator
                item {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(25))
                            .background(color = MaterialTheme.colorScheme.primaryContainer)

                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Can't Decide What to Watch? Let Us Pick for You!",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                            Button(onClick = {
                                val randomMovie = movieList.value.random()
                                val movieJson = Gson().toJson(randomMovie)
                                navController.navigate("movieDetailScreen/$movieJson")
                            }) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    LottieAnimationSmall("https://lottie.host/2119e6f4-1b19-4929-af9b-cb0944c4f614/q7PxEM7fRl.lottie")
                                    Text(text = "Suggest Movie")
                                }

                            }
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
