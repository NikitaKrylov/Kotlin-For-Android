package com.example.myapplication.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.myapplication.Routes
import com.example.myapplication.data.converters.DataConverter
import com.example.myapplication.data.repository.DataRepository
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
    private val repository: DataRepository,
    private val converter: DataConverter
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
        val entity = repository.getLocaleRecipe(id)
        return entity?.let { converter.recipeEntityToDetail(it) }
    }

    private suspend fun writeCache(detail: RecipeDetail) {
        repository.writeLocalRecipe(converter.recipeDetailToEntity(detail))
    }

    private suspend fun getRemoteRecipe(id: String): RecipeDetail? {
        val meal = repository.getRecipeById(id)?.meals?.firstOrNull()
        return meal?.let {  converter.mealDataModelToRecipeDetail(it) }
    }

}




