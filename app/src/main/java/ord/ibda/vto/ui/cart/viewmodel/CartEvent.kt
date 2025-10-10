package ord.ibda.vto.ui.cart.viewmodel

sealed class CartEvent {
    data object LoadCart : CartEvent()
    data class IncreaseQuantity(val cartId: Int) : CartEvent()
    data class DecreaseQuantity(val cartId: Int) : CartEvent()
    data class DeleteItem(val cartId: Int) : CartEvent()
}