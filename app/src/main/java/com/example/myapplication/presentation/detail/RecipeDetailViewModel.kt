package com.example.myapplication.presentation.detail

import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import com.example.myapplication.presentation.model.Ingredient
import com.example.myapplication.presentation.model.RecipeDetail
import com.example.myapplication.presentation.model.RecipeIngredient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random


sealed interface RecipeDetailUiState {
    data object Loading : RecipeDetailUiState
    data class Recipe(
        val value: RecipeDetail,
    ) : RecipeDetailUiState
}

class RecipeDetailViewModel : ViewModel() {
    private val mutableState = MutableStateFlow<RecipeDetailUiState>(RecipeDetailUiState.Loading)
    val state = mutableState.asStateFlow()

    init {
        mutableState.update {
            RecipeDetailUiState.Recipe(
                value = RecipeDetail(
                    id = Random.nextInt(),
                    imageRes = R.drawable.food_image,
                    category = "Classic Japanese recipe",
                    title = "Stuffed eggplants with salad",
                    ingredients = List(10) {
                        RecipeIngredient(
                            ingredient = Ingredient(
                                iconRes = R.drawable.image_22,
                                name = "Eggplant",
                            ),
                            amount = "${Random.nextInt(50, 1000)}g"
                        )
                    }
                )
            )
        }
    }
}