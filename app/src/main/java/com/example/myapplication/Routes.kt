package com.example.myapplication

sealed class Routes(
    val route: String,
) {
    data object Home : Routes("main")
    data class RecipeDetail(
        val id: Int,
    ) : Routes("detail")
}