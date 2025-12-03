package com.example.myapplication.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.converters.DataConverter
import com.example.myapplication.data.remote.FoodApi
import com.example.myapplication.data.remote.MealDataModel
import com.example.myapplication.data.remote.MealsResponse
import com.example.myapplication.data.repository.DataRepository
import com.example.myapplication.presentation.model.RecipePreview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val converter: DataConverter,
    private val repository: DataRepository,
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow<List<RecipePreview>>(listOf())
    val homeScreenState = _homeScreenState.asStateFlow()

    init {
        getUIMeals()
    }

    private fun getUIMeals() {
        viewModelScope.launch {
            val allMeals: List<RecipePreview> = getRecipes()
                .mapNotNull { it.meals }
                .flatten()
                .map { converter.mealDataModelToPreview(it) }
            _homeScreenState.value = allMeals
            Log.d("FoodDebug", "${allMeals.size}")
        }
    }


    private suspend fun getRecipes(): List<MealsResponse> {
        return repository.getMeals()
    }

}