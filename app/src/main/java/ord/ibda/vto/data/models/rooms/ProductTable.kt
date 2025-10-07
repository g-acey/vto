package ord.ibda.vto.data.models.rooms

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductTable(
    val product_name: String,
    val product_details: String,
    val price: Int,
    val product_image: String,
    val category: String,
    @PrimaryKey(autoGenerate = true)
    val product_id: Int = 0
)
