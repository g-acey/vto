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
        )
    ],
    indices = [Index("user_id")]
)
data class OrderTable(
    val user_id: Int,
    val order_date: Long = System.currentTimeMillis(),
    val status: String? = null,
    val estimated_arrival: String? = null,
    @PrimaryKey(autoGenerate = true)
    val order_id: Int = 0,
)
