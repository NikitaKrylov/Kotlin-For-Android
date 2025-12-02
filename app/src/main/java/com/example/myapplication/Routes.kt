package com.example.myapplication

import kotlinx.serialization.Serializable


@Serializable
sealed class Routes(
    val route: String,
) {
    @Serializable
    data object Home : Routes("main")

    @Serializable
    data class RecipeDetail(
        val id: String,
    ) : Routes("detail")
}