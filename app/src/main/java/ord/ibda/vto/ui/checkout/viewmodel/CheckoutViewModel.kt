package ord.ibda.vto.ui.checkout.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ord.ibda.vto.data.db.CartDao
import ord.ibda.vto.data.db.OrderDao
import ord.ibda.vto.data.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val orderDao: OrderDao,
    private val cartDao: CartDao,
    private val sessionManager: SessionManager
): ViewModel() {

    private val _state = MutableStateFlow(CheckoutState())
    val state: StateFlow<CheckoutState> = _state

    private var currentUserId: Int? = null

    init {
        viewModelScope.launch {
            sessionManager.loggedInUserId.collect { id ->
                currentUserId = id
            }
        }
    }

    fun onEvent(event: CheckoutEvent) {
        when(event) {
            is CheckoutEvent.LoadOrder -> loadOrder(event.orderId)
            CheckoutEvent.PayOrder -> payOrder()
            CheckoutEvent.DeleteOrder -> deleteOrder()
            CheckoutEvent.HideConfirmation -> hideConfirmation()
            CheckoutEvent.ShowConfirmation -> showConfirmation()
            CheckoutEvent.ClearSnackbar -> {
                _state.value = _state.value.copy(snackbarMessage = null)
            }
        }
    }

    private fun loadOrder(orderId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, orderId = orderId)

            orderDao.getOrderProducts(orderId).collect { items ->
                val subtotal = items.sumOf { it.price_at_purchase * it.quantity }
                val shipping = _state.value.shippingCost
                val total = subtotal + shipping

                _state.value = _state.value.copy(
                    isLoading = false,
                    orderItems = items,
                    subtotal = subtotal,
                    totalPrice = total
                )
            }
        }
    }

    private fun payOrder() {
        viewModelScope.launch {
            val orderId = _state.value.orderId ?: return@launch
            val userId = currentUserId ?: return@launch

            cartDao.clearCart(userId)

//            _state.value = _state.value.copy(
//                snackbarMessage = "Order successfully made!"
//            )
        }
    }

    private fun showConfirmation() {
        _state.value = _state.value.copy(showConfirmationDialog = true)
    }

    private fun hideConfirmation() {
        _state.value = _state.value.copy(showConfirmationDialog = false)
    }

    private fun deleteOrder() {
        viewModelScope.launch {
            val orderId = _state.value.orderId ?: return@launch
            orderDao.deleteOrderById(orderId)
            _state.value = _state.value.copy(showConfirmationDialog = false)
        }
    }
}