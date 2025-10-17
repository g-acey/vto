package ord.ibda.vto.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ord.ibda.vto.data.models.CartProductDetail
import ord.ibda.vto.data.models.rooms.CartTable

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartTable)

    @Update
    suspend fun updateCartItem(cartItem: CartTable)

    @Delete
    suspend fun deleteCartItem(cartItem: CartTable)

    @Query("DELETE FROM CartTable WHERE user_id = :userId")
    suspend fun clearCart(userId: Int)

    @Query("""
        SELECT ProductTable.*, CartTable.cart_id, CartTable.quantity
        FROM CartTable
        INNER JOIN ProductTable ON ProductTable.product_id = CartTable.product_id
        WHERE CartTable.user_id = :userId
    """)
    fun getCartItems(userId: Int): Flow<List<CartProductDetail>>

    @Query("SELECT SUM(ProductTable.price * CartTable.quantity) FROM CartTable INNER JOIN ProductTable ON ProductTable.product_id = CartTable.product_id WHERE CartTable.user_id = :userId")
    fun getCartTotalPrice(userId: Int): Flow<Int?>

    @Query("SELECT COUNT(*) FROM CartTable WHERE user_id = :userId")
    fun getCartItemCount(userId: Int): Flow<Int>

    @Query("SELECT * FROM CartTable WHERE user_id = :userId AND product_id = :productId LIMIT 1")
    suspend fun getCartItem(userId: Int, productId: Int): CartTable?

    @Query("UPDATE CartTable SET quantity = :newQuantity WHERE cart_id = :cartId")
    suspend fun updateQuantity(cartId: Int, newQuantity: Int)

    @Query("SELECT * FROM CartTable WHERE cart_id = :cartId AND user_id = :userId LIMIT 1")
    suspend fun getCartItemById(cartId: Int, userId: Int): CartTable?
}

