package com.example.moviesapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviesapp.ui.viewmodel.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel){
    val movieList = mainScreenViewModel.movieList.observeAsState(listOf())
    Scaffold(
        topBar = { CenterAlignedTopAppBar(
            title = { Text("Movie App") }
        ) }
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            items(
                count = movieList.value.count(),
                itemContent = {
                    val movie = movieList.value[it]
                    Card(modifier = Modifier.padding(all = 5.dp)) {
                        Text(movie.name)
                    }
                }
            )
        }

    }
}