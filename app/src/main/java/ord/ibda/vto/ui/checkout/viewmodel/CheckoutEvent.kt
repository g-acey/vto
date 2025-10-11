package ord.ibda.vto.ui.checkout.viewmodel

sealed class CheckoutEvent {
    data class LoadOrder(val orderId: Int) : CheckoutEvent()
    data object PayOrder : CheckoutEvent()
    object ShowConfirmation : CheckoutEvent()
    object HideConfirmation : CheckoutEvent()
    object DeleteOrder : CheckoutEvent()
    object ClearSnackbar : CheckoutEvent()
}