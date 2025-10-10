package ord.ibda.vto.ui.cart.viewmodel

import ord.ibda.vto.data.models.CartProductDetail

data class CartState(
    val cartItems: List<CartProductDetail> = emptyList(),
    val totalPrice: Int = 0,
    val itemCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)