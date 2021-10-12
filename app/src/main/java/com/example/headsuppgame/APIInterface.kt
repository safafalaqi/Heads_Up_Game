package com.example.headsuppgame
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("/celebrities/")
    fun getUsersInfo(): Call<Celebrities?>?


    @Headers("Content-Type: application/json")
    @POST("/celebrities/")
    fun addUsersInfo(@Body requestBody: CelebritiesItem?): Call<CelebritiesItem>

    @Headers("Content-Type: application/json")
    @PUT("/celebrities/{id}")
    fun updateUsersInfo(@Path("id")id:Int, @Body requestBody: CelebritiesItem?): Call<CelebritiesItem>

    @Headers("Content-Type: application/json")
    @DELETE("/celebrities/{id}")
    fun deleteUsersInfo(@Path("id")id:Int): Call<Void>

}
