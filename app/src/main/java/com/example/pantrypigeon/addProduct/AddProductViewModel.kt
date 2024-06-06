package com.example.pantrypigeon.addProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypigeon.ProductEvent
import com.example.pantrypigeon.ProductState
import com.example.pantrypigeon.data.Product
import com.example.pantrypigeon.data.ProductRepository
import com.example.pantrypigeon.data.database.ProductDao
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val dao: ProductDao,
    private val repository: ProductRepository
) : ViewModel() {

    private val _products = dao.getProductByNewestEntry()
    private val _state = MutableStateFlow(ProductState())
    val db = Firebase.firestore

    val state = combine(_state, _products) { state, products ->
        state.copy(
            products = products
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())

    fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.DeleteProduct -> {
                viewModelScope.launch {
                    dao.deleteProduct(event.product)
                }
            }

            is ProductEvent.SetExpirationDate -> {
                _state.update {
                    it.copy(
                        expirationDate = event.expirationDate
                    )
                }
            }

            is ProductEvent.SetProductImage -> {
                _state.update {
                    it.copy(
                        productImage = event.productImage
                    )
                }
            }

            is ProductEvent.SetProductName -> {
                _state.update {
                    it.copy(
                        productName = event.productName
                    )
                }
            }

            is ProductEvent.SetStorageLocation -> {
                _state.update {
                    it.copy(
                        storageLocation = event.storageLocation
                    )
                }
            }

            ProductEvent.SaveProduct -> {
                val productName = state.value.productName
                val expirationDate = state.value.expirationDate
                val storageLocation = state.value.storageLocation
                val productImage = state.value.productImage
                if (productName.isBlank() || expirationDate == Date()) {
                    return
                } else {
                    val product = Product(
                        productName = productName,
                        expirationDate = expirationDate,
                        storageLocation = storageLocation,
                        productImage = productImage,
                    )
                    viewModelScope.launch {
                        repository.saveProduct(product)
                    }
                }
            }
        }
    }
}