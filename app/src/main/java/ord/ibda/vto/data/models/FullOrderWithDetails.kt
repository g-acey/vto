package ord.ibda.vto.data.models

import ord.ibda.vto.data.models.rooms.OrderTable

data class FullOrderWithDetails(
    val order: OrderTable,
    val items: List<OrderProductDetail>
)
