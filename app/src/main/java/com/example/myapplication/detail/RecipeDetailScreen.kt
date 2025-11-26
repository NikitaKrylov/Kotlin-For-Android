package com.example.myapplication.detail

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.HeaderIconButton
import com.example.myapplication.R
import com.example.myapplication.data.Ingredient
import com.example.myapplication.data.RecipeDetail
import com.example.myapplication.home.HomeViewModel
import kotlin.random.Random


@Composable
fun RecipeDetailScreen(
    navigateBack: () -> Unit,
    viewModel: RecipeDetailViewModel = viewModel(),
) {
    
    val state = viewModel.state.collectAsStateWithLifecycle().value

    when (state) {
        RecipeDetailUiState.Loading -> {

        }
        is RecipeDetailUiState.Recipe -> {
            RecipeDetail(
                state = state,
                navigateBack = navigateBack,
            )
        }
    }
}


@Composable
private fun RecipeDetail(
    state: RecipeDetailUiState.Recipe,
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            Header(
                navigateBack = navigateBack,
                modifier = Modifier
                    .padding(top = 42.dp)
                    .padding(horizontal = 24.dp)
            )
        }
    ) { _ ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.food_image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Body(
                state = state.value,
                modifier = Modifier,
            )
        }
    }
}

@Composable
fun Body(
    state: RecipeDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 50.dp)
            .padding(horizontal = 20.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = state.title,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = state.category,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 5.dp),
                color = Color(0xFF9DA5C1),
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth()
        ) {
            TintIcon(
                iconRec = R.drawable.chef,
                text = "Easy",
            )
            TintIcon(
                iconRec = R.drawable.time_circle,
                text = "40 min",
            )
            TintIcon(
                iconRec = R.drawable.hot,
                text = "384 kcal"
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Ingreditnts",
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp
            )
            Spacer(Modifier.size(10.dp))
            Text(
                text = state.ingredients.count().toString(),
                fontWeight = FontWeight.Light,
                fontSize = 19.sp,
                color = Color(0xFF9DA5C1),
            )
        }



        Column {
            state.ingredients.forEach { ingredient ->
                IngredientItem(
                    item = ingredient.ingredient,
                    amount = ingredient.amount,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
            }
        }

    }
}


@Composable
fun IngredientItem(
    item: Ingredient,
    amount: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(item.iconRes),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )

            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
            )
        }


        Text(
            text = amount,
            fontWeight = FontWeight.SemiBold,
        )
    }
}


@Composable
fun TintIcon(
    iconRec: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .then(modifier)
    ) {
        Icon(
            painter = painterResource(iconRec),
            contentDescription = null,
            tint = Color(0xFFFFE633),
        )

        Text(
            text = text,
            fontWeight = FontWeight.Bold,
        )
    }
}


@Composable
private fun Header(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        HeaderIconButton(
            iconRes = R.drawable.arrow___left,
            onClick = navigateBack
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            HeaderIconButton(
                iconRes = R.drawable.buy,
                onClick = {}
            )

            HeaderIconButton(
                iconRes = R.drawable.heart,
                onClick = {},
            )
        }
     }
}




@Preview
@Composable
private fun RecipeDetailScreenPreview() {
    RecipeDetailScreen(
        navigateBack = {}
    )
}