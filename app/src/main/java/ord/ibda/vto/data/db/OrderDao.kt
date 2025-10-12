package ord.ibda.vto.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ord.ibda.vto.data.models.OrderProductDetail
import ord.ibda.vto.data.models.OrderWithItems
import ord.ibda.vto.data.models.rooms.OrderItemTable
import ord.ibda.vto.data.models.rooms.OrderTable

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderTable): Long

    @Query("SELECT * FROM OrderTable WHERE user_id = :userId ORDER BY order_date DESC")
    fun getOrdersByUser(userId: Int): Flow<List<OrderTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItem(orderItem: OrderItemTable)

    @Query("""
        SELECT ProductTable.*, OrderItemTable.quantity, OrderItemTable.price_at_purchase
        FROM OrderItemTable
        INNER JOIN ProductTable ON ProductTable.product_id = OrderItemTable.product_id
        WHERE OrderItemTable.order_id = :orderId
    """)
    fun getOrderProducts(orderId: Int): Flow<List<OrderProductDetail>>

    @Query("""
    SELECT ProductTable.*, OrderItemTable.quantity, OrderItemTable.price_at_purchase
    FROM OrderItemTable
    INNER JOIN ProductTable ON ProductTable.product_id = OrderItemTable.product_id
    WHERE OrderItemTable.order_id = :orderId
""")
    suspend fun getOrderProductDetails(orderId: Int): List<OrderProductDetail>

    @Query("DELETE FROM OrderTable WHERE order_id = :orderId")
    suspend fun deleteOrderById(orderId: Int)

    @Query("DELETE FROM OrderTable WHERE status IS NULL")
    suspend fun deleteAbandonedOrders()

    @Transaction
    @Query("SELECT * FROM OrderTable WHERE user_id = :userId ORDER BY order_date DESC")
    suspend fun getAllOrdersWithItems(userId: Int): List<OrderWithItems>

    @Transaction
    @Query("SELECT * FROM OrderTable WHERE user_id = :userId AND status = 'In Progress' ORDER BY order_date DESC")
    suspend fun getInProgressOrdersWithItems(userId: Int): List<OrderWithItems>

    @Transaction
    @Query("SELECT * FROM OrderTable WHERE user_id = :userId AND status = 'Complete' ORDER BY order_date DESC")
    suspend fun getCompletedOrdersWithItems(userId: Int): List<OrderWithItems>

    @Query("UPDATE OrderTable SET status = :newStatus WHERE order_id = :orderId")
    suspend fun updateOrderStatus(orderId: Int, newStatus: String)
}