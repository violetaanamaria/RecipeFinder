package com.example.recipefinder.data.repository

import com.example.recipefinder.data.local.MealDao
import com.example.recipefinder.data.model.FavoriteMeal
import com.example.recipefinder.data.model.Meal
import com.example.recipefinder.data.network.MealApiService
import kotlinx.coroutines.flow.Flow

class MealRepository(
    private val api: MealApiService,
    private val mealDao: MealDao
) {
    suspend fun searchMeals(query: String): List<Meal> {
        return api.searchMeals(query).meals ?: emptyList()
    }

    fun getFavoriteMeals(): Flow<List<FavoriteMeal>> {
        return mealDao.getAll()
    }

    suspend fun addFavorite(meal: FavoriteMeal) {
        mealDao.insert(meal)
    }

    suspend fun removeFavorite(meal: FavoriteMeal) {
        mealDao.deleteById(meal.idMeal)
    }

    suspend fun clearFavorites() {
        mealDao.deleteAll()
    }
}
