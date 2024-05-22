import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypigeon.Product
import com.example.pantrypigeon.ProductDao
import com.example.pantrypigeon.ProductEvent
import com.example.pantrypigeon.ProductState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class ProductViewModel(
    private val dao: ProductDao
) : ViewModel() {

    private val _stateProductDetails: MutableStateFlow<Product?> = MutableStateFlow(null)
    val stateProductDetails: StateFlow<Product?> = _stateProductDetails

    private val _products = dao.getProductByNewestEntry()
    private val _state = MutableStateFlow(ProductState())
    val state = combine(_state, _products) { state, products ->
        state.copy(
            products = products
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())

    fun getProductDetailsById(id: Int) {
        viewModelScope.launch {
            _stateProductDetails.value = dao.getProductDetailsById(id)

        }
    }

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
                if (productName.isBlank() || expirationDate == Date() || storageLocation.isBlank()) {
                    return
                } else {
                    val product = Product(
                        productName = productName,
                        expirationDate = expirationDate,
                        storageLocation = storageLocation,
                        productImage = productImage
                    )
                    viewModelScope.launch {
                        dao.insertProduct(product)
                    }
                }
            }
        }
    }
}