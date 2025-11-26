package com.example.myapplication

import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ExampleViewModel : ViewModel() {
    private val mutableState = MutableStateFlow(0)
    val state = mutableState.asStateFlow()

    fun update() {
        mutableState.update { it + 1 }
    }
}