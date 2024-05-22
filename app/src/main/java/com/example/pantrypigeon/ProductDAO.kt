package com.example.pantrypigeon

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_information ORDER BY expiration_date DESC")
    fun getProductByOldestDates(): Flow<List<Product>>

    @Query("SELECT * FROM product_information ORDER BY id ASC")
    fun getProductByNewestEntry(): Flow<List<Product>>

    @Query("SELECT * FROM product_information WHERE id = :id")
     suspend fun getProductDetailsById(id:Int): Product?

    @Insert
    suspend fun insertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)
}