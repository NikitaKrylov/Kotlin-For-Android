package com.example.myapplication.presentation.model

data class RecipeDetail(
    val id: String,
    val imageRes: String,
    val category: String,
    val title: String,
    val ingredients: List<RecipeIngredient>
)
