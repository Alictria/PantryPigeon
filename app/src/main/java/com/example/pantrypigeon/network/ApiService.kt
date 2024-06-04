package com.example.pantrypigeon.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("filter.php")
    suspend fun getPosts(@Query("i") productName: String): MealBlock
}