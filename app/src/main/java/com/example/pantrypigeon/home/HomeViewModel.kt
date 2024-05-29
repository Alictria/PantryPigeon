package com.example.pantrypigeon.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypigeon.ProductState
import com.example.pantrypigeon.database.ProductDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dao: ProductDao
) : ViewModel() {

    private val _oldestProducts = dao.getProductByOldestDates()
    private val _oldestProductState = MutableStateFlow(ProductState())
    val oldestProductState =
        combine(_oldestProductState, _oldestProducts) { productState, oldestProduct ->
            productState.copy(
                products = oldestProduct
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())
}