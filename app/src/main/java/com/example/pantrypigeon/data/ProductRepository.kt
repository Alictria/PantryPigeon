package com.example.pantrypigeon.data

import android.util.Log
import com.example.pantrypigeon.data.database.DatabaseProduct
import com.example.pantrypigeon.data.database.ProductDao
import com.example.pantrypigeon.data.firebase.FirebaseProduct
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val dao: ProductDao
) {
    val db = Firebase.firestore
    val useLocal: Boolean = true

    suspend fun saveProduct(product: Product) {

        if (useLocal) {

            dao.insertProduct(product.toDatabase())

        } else {
            val firebaseProduct = product.toFirebase()
            runCatching {
                db.collection("products")
                    .document(firebaseProduct.id).set(firebaseProduct).await()
            }.fold({}, { e ->
                Log.w("ProductViewModel", "Error adding document", e)
            })
        }
    }

    private fun Product.toDatabase() = DatabaseProduct(
        productName = productName,
        expirationDate = expirationDate,
        storageLocation = storageLocation,
        productImage = productImage,
    )

    private fun Product.toFirebase() = FirebaseProduct(
        productName = productName,
        expirationDate = expirationDate,
        storageLocation = storageLocation,
        productImage = productImage,
    )


}