package com.example.myapplication.presentation.model

data class RecipeDetail(
    val id: Int,
    val imageRes: Int,
    val category: String,
    val title: String,
    val ingredients: List<RecipeIngredient>
)
