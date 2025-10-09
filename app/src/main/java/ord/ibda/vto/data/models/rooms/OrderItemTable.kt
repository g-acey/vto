package ord.ibda.vto.data.models.rooms

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = OrderTable::class,
            parentColumns = ["order_id"],
            childColumns = ["order_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductTable::class,
            parentColumns = ["product_id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("order_id"), Index("product_id")]
)
data class OrderItemTable(
    val order_id: Int,
    val product_id: Int,
    val quantity: Int,
    val price_at_purchase: Int,
    @PrimaryKey(autoGenerate = true)
    val order_item_id: Int = 0
)
