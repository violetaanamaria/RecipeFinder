package com.example.recipefinder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteMeal(
    @PrimaryKey val idMeal: String,
    val name: String,
    val thumbnailUrl: String
)
