package com.example.moviesapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoadingAnimation(){
    //Composition for Lottie
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Url("https://lottie.host/acb05c5e-96f0-4c9c-b816-d4b922aa4344/3J8DGFR3Iv.lottie")
    )

    LottieAnimation(composition, iterations = LottieConstants.IterateForever)
}