package ord.ibda.vto.data.models

import androidx.room.Embedded
import ord.ibda.vto.data.models.rooms.ProductTable

data class CartProductDetail(
    @Embedded val product: ProductTable,
    val cart_id: Int,
    val quantity: Int
)