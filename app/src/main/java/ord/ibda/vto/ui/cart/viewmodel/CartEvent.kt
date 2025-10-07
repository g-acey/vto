package ord.ibda.vto.ui.cart.viewmodel

sealed class CartEvent {
    object DeleteItem: CartEvent()
    object EditItem: CartEvent()
    object ProcessOrder: CartEvent()
}