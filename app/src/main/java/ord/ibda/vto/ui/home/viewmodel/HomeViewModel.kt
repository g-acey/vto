package ord.ibda.vto.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import ord.ibda.vto.data.db.ProductDao
import ord.ibda.vto.data.models.ProductType
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dao: ProductDao
): ViewModel() {

    private val _productType = MutableStateFlow(ProductType.ALL)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _products = _productType
        .flatMapLatest { productType ->
            when(productType) {
                ProductType.ALL -> dao.getAllProducts()
                ProductType.TEEN -> dao.getProductsByCategory("Teen")
                ProductType.CASUAL -> dao.getProductsByCategory("Casual")
                ProductType.SPORTS -> dao.getProductsByCategory("Sports")
                ProductType.SUMMER -> dao.getProductsByCategory("Summer")
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(HomeState())
    val state = combine(_state, _productType, _products) { state, productType, products ->
        state.copy(
            products = products,
            productType = productType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.SortProducts -> {
                _productType.value = event.productType
            }
        }
    }
}