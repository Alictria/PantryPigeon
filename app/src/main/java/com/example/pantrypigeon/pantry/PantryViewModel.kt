package com.example.pantrypigeon.pantry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypigeon.ProductState
import com.example.pantrypigeon.data.database.ProductDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PantryViewModel @Inject constructor(
    dao: ProductDao
) : ViewModel() {

    private val _newestProducts = dao.getProductByNewestEntry()
    private val _newestProductState = MutableStateFlow(ProductState())

    val newestProductState =
        combine(_newestProductState, _newestProducts) { productState, newestProduct ->
            productState.copy(
                products = newestProduct
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())

}