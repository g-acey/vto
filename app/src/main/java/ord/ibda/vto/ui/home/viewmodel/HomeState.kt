package ord.ibda.vto.ui.home.viewmodel

import ord.ibda.vto.data.models.ProductType
import ord.ibda.vto.data.models.rooms.ProductTable

data class HomeState(
    val products: List<ProductTable> = emptyList(),
    val productType: ProductType = ProductType.ALL
)