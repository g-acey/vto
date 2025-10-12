package ord.ibda.vto.ui.myorder.viewmodel

import ord.ibda.vto.data.models.FullOrderWithDetails
import ord.ibda.vto.data.models.OrderStatus

data class MyOrderState(
    val orders: List<FullOrderWithDetails> = emptyList(),
    val orderStatus: OrderStatus = OrderStatus.ALL
)