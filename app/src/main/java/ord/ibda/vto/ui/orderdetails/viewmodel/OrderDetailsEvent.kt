package ord.ibda.vto.ui.orderdetails.viewmodel

sealed class OrderDetailsEvent {
    data class LoadOrder(val orderId: Int) : OrderDetailsEvent()
}