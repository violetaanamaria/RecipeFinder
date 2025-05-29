package com.example.recipefinder.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recipefinder.viewmodel.MealViewModel
import com.example.recipefinder.data.model.FavoriteMeal

@Composable
fun FavoriteScreen(viewModel: MealViewModel, navController: NavController) {
    val favorites = viewModel.favoriteMeals.collectAsState(initial = emptyList()).value

    if (favorites.isEmpty()) {
        Text("No favorites", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(favorites) { meal: FavoriteMeal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate("detail/${meal.idMeal}") }
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(meal.thumbnailUrl),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(meal.name, modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
            }
        }
    }
}
