package com.example.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
internal data class MealsResponse(
    val meals: List<MealDataModel>? = null
)