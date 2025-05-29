package com.example.recipefinder.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipefinder.viewmodel.MealViewModel
import com.example.recipefinder.viewmodel.MealUiState
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import coil.compose.rememberAsyncImagePainter
import com.example.recipefinder.data.model.Meal

@Composable
fun SearchScreen(navController: NavController, viewModel: MealViewModel) {
    val state by viewModel.uiState.collectAsState()

    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search for a recipe") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = { viewModel.searchMeals(query) }) {
            Text("Search")
        }

        when (val result = state) {
            is MealUiState.Loading -> CircularProgressIndicator()
            is MealUiState.Error -> Text("An error occurred: ${result.message}")
            is MealUiState.Empty -> Text("No recipes found.")
            is MealUiState.Success -> LazyColumn {
                items(result.meals) { meal ->
                    MealItem(meal) {
                        navController.navigate("detail/${meal.idMeal}")
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
fun MealItem(meal: Meal, onClick: () -> Unit) {
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable(onClick = onClick)) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(meal.strMealThumb),
                contentDescription = meal.strMeal,
                modifier = Modifier.size(80.dp)
            )
            Text(meal.strMeal, modifier = Modifier.padding(8.dp))
        }
    }
}
