package com.example.moviesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesapp.data.entity.Movie
import com.example.moviesapp.ui.screens.MainScreen
import com.example.moviesapp.ui.screens.MovieDetail
import com.example.moviesapp.ui.viewmodel.MainScreenViewModel
import com.google.gson.Gson

@Composable
fun ScreenNavigation(mainScreenViewModel: MainScreenViewModel){
    val navController = rememberNavController()

    NavHost(navController= navController, startDestination = "mainScreen"){
        composable(route = "mainScreen"){
            MainScreen(mainScreenViewModel, navController)
        }
        composable(
            route = "movieDetailScreen/{movie}",
            arguments = listOf(navArgument("movie"){type = NavType.StringType})
        ){
            val movieJson = it.arguments?.getString("movie")
            val movieObject = Gson().fromJson(movieJson, Movie::class.java)
            MovieDetail(movie = movieObject)
        }
    }
}