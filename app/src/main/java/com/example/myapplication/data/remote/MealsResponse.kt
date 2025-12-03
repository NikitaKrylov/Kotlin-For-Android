package com.example.myapplication.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class MealsResponse(
    val meals: List<MealDataModel>? = null
)