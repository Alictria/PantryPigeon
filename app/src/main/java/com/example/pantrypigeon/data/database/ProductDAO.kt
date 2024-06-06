package com.example.pantrypigeon.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_information ORDER BY expiration_date ASC")
    fun getProductByOldestDates(): Flow<List<DatabaseProduct>>

    @Query("SELECT * FROM product_information ORDER BY id ASC")
    fun getProductByNewestEntry(): Flow<List<DatabaseProduct>>

    @Query("SELECT * FROM product_information WHERE id = :id")
    suspend fun getProductDetailsById(id: Int): DatabaseProduct?

    @Insert
    suspend fun insertProduct(product: DatabaseProduct)

    @Delete
    suspend fun deleteProduct(product: DatabaseProduct)
}