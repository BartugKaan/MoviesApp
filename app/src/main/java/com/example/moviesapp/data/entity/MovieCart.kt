package com.example.moviesapp.data.entity

data class MovieCart(val cartId: Int,
                     val name: String
                     , val image:String,
                     var price: Int,
                     val category: String,
                     val rating:Double,
                     val year:Int,
                     val director: String,
                     val description: String,
                     var orderAmount: Int,
                     val userName:String) {
}