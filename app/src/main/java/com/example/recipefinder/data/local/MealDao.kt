package com.example.recipefinder.data.local

import androidx.room.*
import com.example.recipefinder.data.model.FavoriteMeal
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Query("SELECT * FROM favorites")
    fun getAll(): Flow<List<FavoriteMeal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: FavoriteMeal)

    @Query("DELETE FROM favorites WHERE idMeal = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM favorites")
    suspend fun deleteAll()
}
