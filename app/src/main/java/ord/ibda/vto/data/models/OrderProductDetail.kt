package ord.ibda.vto.data.models

import androidx.room.Embedded
import ord.ibda.vto.data.models.rooms.ProductTable

data class OrderProductDetail(
    @Embedded val product: ProductTable,
    val quantity: Int,
    val price_at_purchase: Int
)

