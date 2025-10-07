package ord.ibda.vto.ui.home.viewmodel

import ord.ibda.vto.data.models.ProductType

sealed class HomeEvent {
    data class SortProducts(val productType: ProductType): HomeEvent()
}