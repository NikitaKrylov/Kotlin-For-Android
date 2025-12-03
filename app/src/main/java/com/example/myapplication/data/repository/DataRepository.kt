package com.example.myapplication.data.repository

import com.example.myapplication.data.locale.MealsDao
import com.example.myapplication.data.locale.RecipeEntity
import com.example.myapplication.data.remote.FoodApi
import com.example.myapplication.data.remote.FoodService
import com.example.myapplication.data.remote.MealsResponse


import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DataRepository @Inject constructor(
    private val localDataSource: MealsDao,
) {




    suspend fun getMeals(): List<MealsResponse> = coroutineScope {

        val results = mutableListOf<Deferred<MealsResponse>>()

        // Создаем 10 асинхронных запросов
        repeat(10) {
            val deferred: Deferred<MealsResponse> = async(context = Dispatchers.IO) {
                FoodApi.retrofitService.getRandomMeal()
            }
            results.add(deferred)
        }

        // Ждем завершения всех запросов и собираем результаты
        return@coroutineScope results.awaitAll()

    }

    suspend fun getRecipeById(recipeId: String): MealsResponse? = withContext(Dispatchers.IO) {
        FoodApi.retrofitService.getRecipeById(recipeId)
    }

    suspend fun getLocaleRecipe(id: String): RecipeEntity? {
        return localDataSource.getById(id)
    }

    suspend fun writeLocalRecipe(detail: RecipeEntity) {
        localDataSource.insertAll(detail)
    }



}