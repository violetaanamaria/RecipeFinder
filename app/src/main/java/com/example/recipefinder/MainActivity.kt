package com.example.recipefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.room.Room
import com.example.recipefinder.data.local.AppDatabase
import com.example.recipefinder.data.network.MealApiService
import com.example.recipefinder.data.repository.MealRepository
import com.example.recipefinder.ui.navigation.AppNavigation
import com.example.recipefinder.ui.theme.RecipeFinderTheme
import com.example.recipefinder.viewmodel.MealViewModel
import com.example.recipefinder.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = MealApiService.create()
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "recipe-db")
            .fallbackToDestructiveMigration()
            .build()

        val mealViewModel = MealViewModel(MealRepository(api, db.mealDao()))
        val settingsViewModel = SettingsViewModel()

        setContent {
            val isDarkTheme by settingsViewModel.darkTheme.collectAsState()

            RecipeFinderTheme(darkTheme = isDarkTheme) {
                AppNavigation(
                    mealViewModel = mealViewModel,
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }
}
