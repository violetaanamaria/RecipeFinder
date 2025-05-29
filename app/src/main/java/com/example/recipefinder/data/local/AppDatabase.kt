package com.example.recipefinder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipefinder.data.model.FavoriteMeal

@Database(entities = [FavoriteMeal::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}
