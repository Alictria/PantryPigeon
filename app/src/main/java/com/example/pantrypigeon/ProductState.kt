package com.example.pantrypigeon

import com.example.pantrypigeon.database.Product
import java.util.Date

data class ProductState(
    val products: List<Product> = emptyList(),
    val productName: String = "",
    val expirationDate: Date = Date(),
    val storageLocation: String = "",
    val productImage: ByteArray? = null,
    val id:Int = 0
)