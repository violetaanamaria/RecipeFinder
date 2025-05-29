package com.example.recipefinder.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import coil.compose.rememberAsyncImagePainter
import com.example.recipefinder.viewmodel.MealViewModel
import com.example.recipefinder.viewmodel.MealUiState
import com.example.recipefinder.data.model.FavoriteMeal
import com.example.recipefinder.data.model.Meal
import com.example.recipefinder.data.model.getIngredient
import com.example.recipefinder.data.model.getMeasure



@Composable
fun DetailScreen(mealId: String, viewModel: MealViewModel) {
    val state by viewModel.uiState.collectAsState()
    val meal = (state as? MealUiState.Success)?.meals?.find { it.idMeal == mealId }
    val isFavorite by viewModel.isFavorite(mealId).collectAsState(initial = false)

    meal?.let {
        val ingredients = buildList {
            for (i in 1..20) {
                val ingredient = it.getIngredient(i)
                val measure = it.getMeasure(i)
                if (!ingredient.isNullOrBlank()) {
                    add("$ingredient - ${measure ?: ""}")
                }
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(it.strMealThumb),
                contentDescription = it.strMeal,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(it.strMeal, style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Ingredients:", style = MaterialTheme.typography.titleMedium)
            ingredients.forEach { ing ->
                Text("• $ing")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Instructions:", style = MaterialTheme.typography.titleMedium)
            Text(it.strInstructions ?: "No instructions.")

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (isFavorite) {
                        viewModel.removeFavorite(
                            FavoriteMeal(
                                idMeal = it.idMeal,
                                name = it.strMeal,
                                thumbnailUrl = it.strMealThumb
                            )
                        )
                    } else {
                        viewModel.addFavorite(
                            FavoriteMeal(
                                idMeal = it.idMeal,
                                name = it.strMeal,
                                thumbnailUrl = it.strMealThumb
                            )
                        )
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(if (isFavorite) "Remove from Favorites" else "Add to Favorites")
            }
        }
    } ?: Text("Meal not found.")
}
