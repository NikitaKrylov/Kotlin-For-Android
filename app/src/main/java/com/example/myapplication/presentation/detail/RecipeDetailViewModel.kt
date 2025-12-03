package com.example.myapplication.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.myapplication.Routes
import com.example.myapplication.data.locale.MealsDao
import com.example.myapplication.data.remote.FoodApi
import com.example.myapplication.data.remote.MealDataModel
import com.example.myapplication.presentation.model.RecipeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface RecipeDetailUiState {
    data object Loading : RecipeDetailUiState
    data class Recipe(
        val value: RecipeDetail,
    ) : RecipeDetailUiState
}


@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mealsDao: MealsDao,
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