package ord.ibda.vto.data.models.rooms

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserTable::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductTable::class,
            parentColumns = ["product_id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("user_id"), Index("product_id")]
)
data class CartTable(
    val user_id: Int,
    val product_id: Int,
    val quantity: Int = 1,
    @PrimaryKey(autoGenerate = true)
    val cart_id: Int = 0
)
