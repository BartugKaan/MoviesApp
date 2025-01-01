package com.example.moviesapp.retrofit

import com.example.moviesapp.data.entity.CRUDResponse
import com.example.moviesapp.data.entity.MovieCartResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

//For Cart functions(Add-Delete-Get)
interface CartDao {
    //Database access Object
    //http://kasimadalan.pe.hu/ -> Base URL
    //movies/getMovieCart.php -> Api Url(get cart)
    //movies/insertMovie.php -> Api Url(Add movie)
    //movies/deleteMovie.php -> Api Url(Delete movie)

    @POST("movies/getMovieCart.php")
    @FormUrlEncoded
    suspend fun getMoviesFromCart(@Field("userName") userName:String) : MovieCartResponse

    @POST("movies/insertMovie.php")
    @FormUrlEncoded
    suspend fun addMovieToCart(@Field("name") name:String,
                               @Field("image") image:String,
                               @Field("price") price:Int,
                               @Field("category") category: String,
                               @Field("rating") rating:Double,
                               @Field("year") year:Int,
                               @Field("director") director:String,
                               @Field("description") description:String,
                               @Field("orderAmount") orderAmount:Int,
                               @Field("userName") userName:String) : CRUDResponse

    @POST
    @FormUrlEncoded
    suspend fun deleteMovieFromCart(@Field("cartId") cartId: Int, @Field("userName") userName:String) : CRUDResponse
}