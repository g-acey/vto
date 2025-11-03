package ord.ibda.vto.ui.vto.viewmodel

sealed class VtoEvent {
    data class SetUserImage(val uri: String): VtoEvent()
    data class SetProductImage(val url: String): VtoEvent()
    data class LoadProductById(val productId: Int) : VtoEvent()
    object RemoveUserImage: VtoEvent()
    object TryOn : VtoEvent()
}