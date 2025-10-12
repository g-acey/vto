package ord.ibda.vto.ui.myorder.viewmodel

import ord.ibda.vto.data.models.OrderStatus

sealed class MyOrderEvent {
    data class SortOrders(val orderStatus: OrderStatus) : MyOrderEvent()
}