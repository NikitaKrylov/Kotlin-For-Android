package com.example.myapplication.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.myapplication.HeaderIconButton
import com.example.myapplication.R
import com.example.myapplication.presentation.model.RecipePreview
import kotlin.random.Random


data class IngredientItem(
    val id: Int,
    val imageRes: Int,
    val name: String,
)

@Composable
internal fun HomeScreen(
    navigateToDetailRecipe: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel(),
) {
    val state = viewModel.homeScreenState.collectAsState()

    Column(
        modifier = Modifier
            .background(Color(0xFFF6F8FC))
            .padding(10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val ingredients = remember {
            List(5) {
                IngredientItem(
                    id = Random.nextInt(),
                    imageRes = R.drawable.image_22,
                    name = "авпва"
                )
            }
        }

        Header()

        Spacer(Modifier.height(40.dp))
        Text(
            text = "Recipes you can make",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            items(state.value) { recipe ->
                PrimaryRecipeItem(
                    imageRes = recipe.imageRes,
                    name = recipe.title,
                    category = recipe.category,
                    onClick = {
                        navigateToDetailRecipe(recipe.id)
                    },
                )
            }
        }

        Spacer(Modifier.height(30.dp))

        Text(
            text = "Ingredients",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            items(ingredients) { ingredient ->
                IngredientItem(
                    imageRes = ingredient.imageRes,
                    name = ingredient.name,
                    onClick = {  },
                )
            }
        }

        Button(onClick = { viewModel.getUIMeals() }) {
            Text("Нажми на меня")
        }
    }
}


private val linearGradientBlack = Brush.verticalGradient(
    0.0f to Color.Transparent,
    0.2f to Color.Black.copy(alpha = 0.4f),
    1.0f to Color.Black
)

@Composable
private fun PrimaryRecipeItem(
    imageRes: String?,
    name: String,
    category: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .size(190.dp, 260.dp)
            .then(modifier)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageRes)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        Text(
            text = category,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 10.dp, start = 10.dp)
        )

        Text(
            text = name,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-20).dp)

        )
    }
}

@Composable
private fun IngredientItem(
    imageRes: Int,
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .then(modifier)
    ) {
        Box(

            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .clickable(onClick = onClick)
                .height(200.dp)
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(120.dp)
            )
        }
        Text(
            text = name,
        )
    }
}


@Composable
private fun Header(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HeaderIconButton(
            iconRes = R.drawable.category,
            onClick = {},
        )
        Text(
            text = "Fridge",
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
        )

        HeaderIconButton(
            iconRes = R.drawable.resource_default,
            onClick = {}
        )
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        navigateToDetailRecipe = {}
    )
}