package com.example.pantrypigeon.data

import com.example.pantrypigeon.addProduct.PantryType
import java.util.Date

data class Product(
    val id: String = "",
    val productName: String,
    val expirationDate: Date = Date(),
    val storageLocation: PantryType,
    val productImage: ByteArray? = null,
)