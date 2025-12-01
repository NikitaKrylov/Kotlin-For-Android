package com.example.myapplication.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.request.CachePolicy
import com.example.myapplication.data.FoodApi
import com.example.myapplication.data.MealDataModel
import com.example.myapplication.data.MealsResponse
import com.example.myapplication.presentation.model.RecipePreview
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class HomeViewModel : ViewModel() {

    private val _homeScreenState = MutableStateFlow<List<RecipePreview>>(listOf())
    val homeScreenState = _homeScreenState.asStateFlow()

    fun getUIMeals() {
        viewModelScope.launch {
            delay(3000)
            val allMeals: List<RecipePreview> = getRecipes()
                .mapNotNull { it.meals }
                .flatten()
                .map { it.toUiModel() }
            _homeScreenState.value = allMeals
            Log.d("FoodDebug", "${allMeals.size}")
        }
    }


    private fun MealDataModel.toUiModel(): RecipePreview =
        with(this) {
            RecipePreview(
                id = idMeal.toInt(),
                imageRes = strMealThumb,
                category = strCategory.orEmpty(),
                title = strMeal.orEmpty()
            )
        }

    private suspend fun getRecipes(): List<MealsResponse> {
        // Список Deferred объектов
        val results = mutableListOf<Deferred<MealsResponse>>()

        // Создаем 10 асинхронных запросов
        repeat(10) {
            val deferred = viewModelScope.async(Dispatchers.IO) {
                FoodApi.retrofitService.getRandomMeal()
            }
            results.add(deferred)
        }

        // Ждем завершения всех запросов и собираем результаты
        return results.awaitAll()
    }

}