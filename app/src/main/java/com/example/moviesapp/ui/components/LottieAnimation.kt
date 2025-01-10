package com.example.moviesapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimation(url: String){
    //Composition for Lottie
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Url(url)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition, iterations = LottieConstants.IterateForever)
    }
}

@Composable
fun LottieAnimationSmall(url: String){
    //Composition for Lottie
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Url(url)
    )
        LottieAnimation(composition, iterations = LottieConstants.IterateForever, modifier = Modifier.size(height = 30.dp, width = 30.dp))
}