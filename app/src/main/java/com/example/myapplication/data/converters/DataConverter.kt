package com.example.myapplication.data.converters


import com.example.myapplication.data.locale.RecipeEntity
import com.example.myapplication.data.remote.MealDataModel
import com.example.myapplication.presentation.model.RecipeDetail
import com.example.myapplication.presentation.model.RecipePreview
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataConverter @Inject constructor() {

    fun mealDataModelToRecipeDetail(model : MealDataModel): RecipeDetail =
        with(model) {
            RecipeDetail(
                id = model.idMeal,
                imageRes = model.strMealThumb.orEmpty(),
                category = model.strCategory.orEmpty(),
                title = model.strMeal.orEmpty(),
                ingredients = emptyList()
            )
        }


    fun recipeDetailToEntity(detail: RecipeDetail): RecipeEntity =
        with(detail) {
            RecipeEntity(
                idMeal = detail.id,
                strMeal = detail.title,
                strCategory = detail.category,
                strMealThumb = detail.imageRes,
            )
        }

    fun recipeEntityToDetail(entity : RecipeEntity): RecipeDetail  =
        with(entity) {
            RecipeDetail(
                id = entity.idMeal,
                imageRes = entity.strMealThumb.orEmpty(),
                category = entity.strCategory.orEmpty(),
                title = entity.strMeal.orEmpty(),
                ingredients = emptyList(),
            )
        }


    fun mealDataModelToPreview(model : MealDataModel): RecipePreview =
        with(model) {
            RecipePreview(
                id = idMeal,
                imageRes = strMealThumb,
                category = strCategory.orEmpty(),
                title = strMeal.orEmpty()
            )
        }
}