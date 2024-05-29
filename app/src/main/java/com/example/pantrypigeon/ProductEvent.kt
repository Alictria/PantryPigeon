package com.example.pantrypigeon

import com.example.pantrypigeon.database.Product
import java.util.Date

sealed interface ProductEvent {
    data object SaveProduct : ProductEvent
    data class SetProductName(val productName: String) : ProductEvent
    data class SetExpirationDate(val expirationDate: Date) : ProductEvent
    data class SetStorageLocation(val storageLocation: String) : ProductEvent
    data class SetProductImage(val productImage: ByteArray?) : ProductEvent
    data class DeleteProduct(val product: Product) : ProductEvent
}