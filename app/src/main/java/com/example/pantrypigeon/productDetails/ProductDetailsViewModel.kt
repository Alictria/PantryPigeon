package com.example.pantrypigeon.productDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypigeon.data.database.DatabaseProduct
import com.example.pantrypigeon.data.database.ProductDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val dao: ProductDao
) : ViewModel() {
    private val _stateProductDetails: MutableStateFlow<DatabaseProduct?> = MutableStateFlow(null)
    val stateProductDetails: StateFlow<DatabaseProduct?> = _stateProductDetails

    fun getProductDetailsById(id: Int) {
        viewModelScope.launch {
            _stateProductDetails.value = dao.getProductDetailsById(id)
        }
    }
}