package com.example.recipefinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.model.Meal
import com.example.recipefinder.data.model.FavoriteMeal
import com.example.recipefinder.data.repository.MealRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class MealUiState {
    object Loading : MealUiState()
    object Empty : MealUiState()
    data class Success(val meals: List<Meal>) : MealUiState()
    data class Error(val message: String) : MealUiState()
}

class MealViewModel(private val repository: MealRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<MealUiState>(MealUiState.Empty)
    val uiState: StateFlow<MealUiState> = _uiState

    val favoriteMeals = repository.getFavoriteMeals().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun searchMeals(query: String) {
        viewModelScope.launch {
            _uiState.value = MealUiState.Loading
            try {
                val meals = repository.searchMeals(query)
                _uiState.value = if (meals.isNotEmpty()) MealUiState.Success(meals) else MealUiState.Empty
            } catch (e: Exception) {
                _uiState.value = MealUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addFavorite(meal: FavoriteMeal) {
        viewModelScope.launch { repository.addFavorite(meal) }
    }

    fun removeFavorite(meal: FavoriteMeal) {
        viewModelScope.launch { repository.removeFavorite(meal) }
    }

    fun isFavorite(mealId: String): Flow<Boolean> =
        repository.getFavoriteMeals().map { list ->
            list.any { it.idMeal == mealId }
        }

    fun clearFavorites() {
        viewModelScope.launch { repository.clearFavorites() }
    }
}
