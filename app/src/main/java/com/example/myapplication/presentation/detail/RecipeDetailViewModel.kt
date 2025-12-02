package com.example.myapplication.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.myapplication.R
import com.example.myapplication.Routes
import com.example.myapplication.data.FoodApi
import com.example.myapplication.data.MealDataModel
import com.example.myapplication.data.MealsResponse
import com.example.myapplication.presentation.model.Ingredient
import com.example.myapplication.presentation.model.RecipeDetail
import com.example.myapplication.presentation.model.RecipeIngredient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.concurrent.timerTask
import kotlin.random.Random


sealed interface RecipeDetailUiState {
    data object Loading : RecipeDetailUiState
    data class Recipe(
        val value: RecipeDetail,
    ) : RecipeDetailUiState
}

class RecipeDetailViewModel constructor(
        savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val route = savedStateHandle.toRoute<Routes.RecipeDetail>()

    private val mutableState = MutableStateFlow<RecipeDetailUiState>(RecipeDetailUiState.Loading)
    val state = mutableState.asStateFlow()


    private suspend fun getRemoteRecipe(): MealDataModel? {
        return FoodApi.retrofitService.getRecipeById(route.id).meals?.firstOrNull()
    }

    init {
        viewModelScope.launch {
            val recipe = getRemoteRecipe() ?: return@launch

            mutableState.update {
                RecipeDetailUiState.Recipe(
                    value = recipe.toUiModel()
                )
            }

        }

    }


    private fun MealDataModel.toUiModel(): RecipeDetail {
        return RecipeDetail(
            id = this.idMeal,
            imageRes = this.strMealThumb.orEmpty(),
            category = this.strCategory.orEmpty(),
            title = this.strMeal.orEmpty(),
            ingredients = emptyList()
        )
    }
}