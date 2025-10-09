package ord.ibda.vto.ui.productdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ord.ibda.vto.data.db.CartDao
import ord.ibda.vto.data.db.ProductDao
import ord.ibda.vto.data.models.rooms.CartTable
import ord.ibda.vto.data.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productDao: ProductDao,
    private val cartDao: CartDao,
    private val sessionManager: SessionManager
): ViewModel() {

    private val _state = MutableStateFlow(ProductDetailsState())
    val state: StateFlow<ProductDetailsState> = _state

    fun onEvent(event: ProductDetailsEvent) {
        when(event) {
            is ProductDetailsEvent.LoadProduct -> loadProduct(event.productId)
            is ProductDetailsEvent.AddToCart -> addToCart(event.productId)
        }
    }

    private fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val product = productDao.getProductById(productId)
                _state.value = _state.value.copy(product = product, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    private fun addToCart(productId: Int) {
        viewModelScope.launch {
            val userId = sessionManager.loggedInUserId.firstOrNull()
            if (userId != null) {
                val existing = cartDao.getCartItem(userId, productId)
                if (existing == null) {
                    cartDao.insertCartItem(
                        CartTable(
                            user_id = userId,
                            product_id = productId,
                            quantity = 1
                        )
                    )
                } else {
                    cartDao.updateQuantity(existing.cart_id, existing.quantity + 1)
                }
                _state.value = _state.value.copy(showSnackbar = true)
            } else {
                _state.value = _state.value.copy(error = "You must be logged in to add to cart.")
            }
        }
    }

    fun onSnackbarShown() {
        _state.value = _state.value.copy(showSnackbar = false)
    }
}