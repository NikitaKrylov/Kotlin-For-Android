package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.myapplication.presentation.detail.RecipeDetailScreen
import com.example.myapplication.presentation.home.HomeScreen
import com.example.myapplication.presentation.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backStack = remember { mutableStateListOf<Any>(Routes.Home) }

            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->


                    NavDisplay(
                        modifier = Modifier.padding(innerPadding),
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryProvider = entryProvider {
                            entry<Routes.Home> {
                                HomeScreen(
                                    navigateToDetailRecipe = { recipeId ->
                                        backStack.add(
                                            Routes.RecipeDetail(
                                                id = recipeId
                                            )
                                        )
                                    }
                                )
                            }

                            entry<Routes.RecipeDetail>(
                                metadata = NavDisplay.transitionSpec {
                                    // Slide new content up, keeping the old content in place underneath
                                    slideInVertically(
                                        initialOffsetY = { it },
                                        animationSpec = tween(1000)
                                    ) togetherWith ExitTransition.KeepUntilTransitionsFinished
                                } + NavDisplay.popTransitionSpec {
                                    // Slide old content down, revealing the new content in place underneath
                                    EnterTransition.None togetherWith
                                            slideOutVertically(
                                                targetOffsetY = { it },
                                                animationSpec = tween(1000)
                                            )
                                } + NavDisplay.predictivePopTransitionSpec {
                                    // Slide old content down, revealing the new content in place underneath
                                    EnterTransition.None togetherWith
                                            slideOutVertically(
                                                targetOffsetY = { it },
                                                animationSpec = tween(1000)
                                            )
                                }
                            ) {
                                RecipeDetailScreen(
                                    navigateBack = backStack::removeLastOrNull
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}