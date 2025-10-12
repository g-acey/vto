package ord.ibda.vto.data.models

import androidx.room.Embedded
import androidx.room.Relation
import ord.ibda.vto.data.models.rooms.OrderItemTable
import ord.ibda.vto.data.models.rooms.OrderTable

data class OrderWithItems(
    @Embedded val order: OrderTable,

    @Relation(
        parentColumn = "order_id",
        entityColumn = "order_id"
    )
    val items: List<OrderItemTable>
)
