package ord.ibda.vto.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ord.ibda.vto.data.models.OrderProductDetail
import ord.ibda.vto.data.models.rooms.OrderItemTable
import ord.ibda.vto.data.models.rooms.OrderTable

@Dao
interface OrderDao {

    // --- ORDERS ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderTable): Long

    @Query("SELECT * FROM OrderTable WHERE user_id = :userId ORDER BY order_date DESC")
    fun getOrdersByUser(userId: Int): Flow<List<OrderTable>>

    @Query("UPDATE OrderTable SET status = :status WHERE order_id = :orderId")
    suspend fun updateOrderStatus(orderId: Int, status: String)

    // --- ORDER ITEMS ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItem(orderItem: OrderItemTable)

    @Query("""
        SELECT ProductTable.*, OrderItemTable.quantity, OrderItemTable.price_at_purchase
        FROM OrderItemTable
        INNER JOIN ProductTable ON ProductTable.product_id = OrderItemTable.product_id
        WHERE OrderItemTable.order_id = :orderId
    """)
    fun getOrderProducts(orderId: Int): Flow<List<OrderProductDetail>>
}