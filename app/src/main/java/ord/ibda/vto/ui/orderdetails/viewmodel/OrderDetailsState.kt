package ord.ibda.vto.ui.orderdetails.viewmodel

import ord.ibda.vto.data.models.FullOrderWithDetails
import ord.ibda.vto.data.models.OrderProductDetail
import ord.ibda.vto.data.models.rooms.OrderTable

data class OrderDetailsState(
    val isLoading: Boolean = false,
    val order: OrderTable? = null,
    val items: List<OrderProductDetail> = emptyList(),
    val subtotal: Int = 0,
    val shippingCost: Int = 50000,
    val totalPrice: Int = 0
)