package com.example.pantrypigeon.data.firebase

import com.example.pantrypigeon.addProduct.PantryType
import java.util.Date
import java.util.UUID

data class FirebaseProduct(
    val id: String = UUID.randomUUID().toString(),
    val productName: String,
    val expirationDate: Date = Date(),
    val storageLocation: PantryType,
    val productImage: ByteArray? = null,
) {
    constructor() : this("", "", Date(), PantryType.PANTRY, null)
}