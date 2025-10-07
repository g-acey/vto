package ord.ibda.vto.data.db

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ord.ibda.vto.data.models.rooms.ProductTable

@Dao
interface ProductDao {

    @Query("SELECT * FROM ProductTable")
    fun getAllProducts(): Flow<List<ProductTable>>

    @Query("SELECT * FROM ProductTable WHERE category = :category")
    fun getProductsByCategory(category: String): Flow<List<ProductTable>>

    @Query("SELECT * FROM ProductTable WHERE product_id = :id")
    suspend fun getProductById(id: Int): ProductTable?
}