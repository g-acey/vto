package ord.ibda.vto.ui.productdetails.viewmodel

import ord.ibda.vto.data.models.rooms.ProductTable

data class ProductDetailsState(
    val product: ProductTable? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showSnackbar: Boolean = false
)