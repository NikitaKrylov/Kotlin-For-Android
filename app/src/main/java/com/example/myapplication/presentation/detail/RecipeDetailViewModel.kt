package com.example.myapplication.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.myapplication.Routes
import com.example.myapplication.data.locale.MealsDao
import com.example.myapplication.data.locale.RecipeEntity
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

    init {
        viewModelScope.launch {
            val recipe = getLocaleRecipe(route.id) ?: getRemoteRecipe(route.id)?.also {
                writeCache(it)
            } ?: return@launch

            mutableState.update {
                RecipeDetailUiState.Recipe(
                    value = recipe
                )
            }
        }
    }

    private  suspend fun getLocaleRecipe(id: String): RecipeDetail? {
        return mealsDao.getById(id)?.toUiModel()
    }

    private suspend fun writeCache(detail: RecipeDetail) {
        mealsDao.insertAll(detail.toEntity())
    }

    private suspend fun getRemoteRecipe(id: String): RecipeDetail? {
        return FoodApi.retrofitService.getRecipeById(id).meals?.firstOrNull()?.toUiModel()
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

private fun RecipeDetail.toEntity(): RecipeEntity {
    return RecipeEntity(
        idMeal = id,
        strMeal = title,
        strCategory = category,
        strMealThumb = imageRes,
    )
}

private fun RecipeEntity.toUiModel(): RecipeDetail {
    return RecipeDetail(
        id = this.idMeal,
        imageRes = strMealThumb.orEmpty(),
        category = strCategory.orEmpty(),
        title = strMeal.orEmpty(),
        ingredients = emptyList(),
    )
}
