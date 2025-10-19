package ord.ibda.vto.ui.orderdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ord.ibda.vto.data.db.OrderDao
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val orderDao: OrderDao
): ViewModel() {

    private val _state = MutableStateFlow(OrderDetailsState())
    val state: StateFlow<OrderDetailsState> = _state

    fun onEvent(event: OrderDetailsEvent) {
        when (event) {
            is OrderDetailsEvent.LoadOrder -> loadOrder(event.orderId)
        }
    }

    private fun loadOrder(orderId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val order = orderDao.getOrderById(orderId)
            val products = orderDao.getOrderProductDetails(orderId)

            val subtotal = products.sumOf { it.price_at_purchase * it.quantity }
            val shipping = _state.value.shippingCost
            val total = subtotal + shipping

            _state.value = _state.value.copy(
                isLoading = false,
                order = order,
                items = products,
                subtotal = subtotal,
                totalPrice = total
            )
        }
    }
}