package com.lj.crud_supabase.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lj.crud_supabase.data.database.dao.CategoryDao
import com.lj.crud_supabase.data.database.dao.LineItemDao
import com.lj.crud_supabase.data.database.dao.OrderDao
import com.lj.crud_supabase.data.database.dao.ProductDao
import com.lj.crud_supabase.data.database.dao.RecipeDao
import com.lj.crud_supabase.data.database.dao.UserDao
import com.lj.crud_supabase.data.database.entities.Category
import com.lj.crud_supabase.data.database.entities.LineItem
import com.lj.crud_supabase.data.database.entities.Order
import com.lj.crud_supabase.data.database.entities.Product
import com.lj.crud_supabase.data.database.entities.Recipe
import com.lj.crud_supabase.data.database.entities.User

@Database(
    entities = [Product::class, LineItem::class, Order::class, Category::class, User::class, Recipe::class],
    version = 1,
    exportSchema = false
)
abstract class CRUDSearchDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun lineItemDao(): LineItemDao
    abstract fun orderDao(): OrderDao
    abstract fun categoryDao(): CategoryDao
    abstract fun userDao(): UserDao
    abstract fun recipeDao(): RecipeDao
}