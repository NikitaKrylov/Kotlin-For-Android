package com.example.myapplication.data.remote

import kotlinx.serialization.Serializable

@Serializable
internal data class MealsResponse(
    val meals: List<MealDataModel>? = null
)