package ord.ibda.vto.ui.checkout.viewmodel

import ord.ibda.vto.data.models.OrderProductDetail

data class CheckoutState(
    val isLoading: Boolean = false,
    val orderId: Int? = null,
    val orderItems: List<OrderProductDetail> = emptyList(),
    val subtotal: Int = 0,
    val shippingCost: Int = 50000,
    val totalPrice: Int = 0,
    val showConfirmationDialog: Boolean = false,
    val snackbarMessage: String? = null
)