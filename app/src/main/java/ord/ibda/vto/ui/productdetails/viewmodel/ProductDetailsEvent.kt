package ord.ibda.vto.ui.productdetails.viewmodel

sealed class ProductDetailsEvent {
    data class LoadProduct(val productId: Int): ProductDetailsEvent()
    data class AddToCart(val productId: Int): ProductDetailsEvent()
}