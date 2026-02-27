package com.lj.crud_supabase.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lj.crud_supabase.data.database.entities.Recipe
import com.lj.crud_supabase.utilities.RECIPE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<Recipe>)

    @Query("SELECT * FROM $RECIPE_TABLE")
    fun getAll(): Flow<List<Recipe>>
}
