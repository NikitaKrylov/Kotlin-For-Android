package com.example.myapplication.data.remote

import dagger.Provides
import retrofit2.Retrofit
import retrofit2.http.GET
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Query


private const val BASE_URL =
    "https://www.themealdb.com/api/json/v1/1/"

private val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

/**
 * OkHttpClient с логированием
 */
private val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
 */
val json = Json { ignoreUnknownKeys = true }
private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

internal object FoodApi {
    val retrofitService: FoodService by lazy {
        retrofit.create(FoodService::class.java)
    }
}

internal interface FoodService {

    @GET("random.php")
    suspend fun getRandomMeal(): MealsResponse


    @GET("lookup.php")
    suspend fun getRecipeById(@Query("i") id: String): MealsResponse

}