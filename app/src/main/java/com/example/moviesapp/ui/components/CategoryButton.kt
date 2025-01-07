package com.example.moviesapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryButton(buttonText:String, buttonAction: () -> Unit){
    OutlinedButton(
        onClick = buttonAction,
        modifier = Modifier.size(height = 50.dp, width = 100.dp),
        colors = ButtonDefaults.buttonColors(

        ),
        shape = RoundedCornerShape(30)
        ) {
        Text(text = buttonText, fontSize = 22.sp)
    }
}

@Preview
@Composable
fun CategoryButtonPreview(){
    MaterialTheme {
        Scaffold { paddingValues ->
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CategoryButton(
                    buttonText = "All"
                ) {
                    println("Clicked")
                }
            }
        }

    }
}
