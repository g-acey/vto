package ord.ibda.vto.ui.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ord.ibda.vto.data.db.CartDao
import ord.ibda.vto.data.db.OrderDao
import ord.ibda.vto.data.models.rooms.CartTable
import ord.ibda.vto.data.models.rooms.OrderItemTable
import ord.ibda.vto.data.models.rooms.OrderTable
import ord.ibda.vto.data.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartDao: CartDao,
    private val orderDao: OrderDao,
    private val sessionManager: SessionManager
): ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state

    private var currentUserId: Int? = null
    private var cartCollectionJob: Job? = null

    init {
        viewModelScope.launch {
            sessionManager.loggedInUserId.collect { id ->
                currentUserId = id
                cartCollectionJob?.cancel()

                if (id != null) {
                    _state.value = _state.value.copy(isLoading = true)
                    loadCart()
                } else {
                    _state.value = CartState()
                }
            }
        }
    }

    fun onEvent(event: CartEvent) {
        when(event) {
            is CartEvent.DecreaseQuantity -> updateQuantity(event.cartId, -1)
            is CartEvent.DeleteItem -> deleteItem(event.cartId)
            is CartEvent.IncreaseQuantity -> updateQuantity(event.cartId, +1)
            CartEvent.LoadCart -> loadCart()
            CartEvent.ProcessOrder -> processOrder()
        }
    }

    private fun loadCart() {
        val userId = currentUserId ?: return
        cartCollectionJob?.cancel() // cancel old collectors before starting new one

        cartCollectionJob = viewModelScope.launch {
            combine(
                cartDao.getCartItems(userId),
                cartDao.getCartTotalPrice(userId),
                cartDao.getCartItemCount(userId)
            ) { items, total, count ->
                Triple(items, total ?: 0, count)
            }.collect { (items, total, count) ->
                _state.value = _state.value.copy(
                    cartItems = items,
                    totalPrice = total,
                    itemCount = count,
                    isLoading = false
                )
            }
        }
    }

    private fun updateQuantity(cartId: Int, delta: Int) {
        viewModelScope.launch {
            val userId = currentUserId ?: return@launch
//            val item = _state.value.cartItems.find { it.cart_id == cartId } ?: return@launch
//            val newQuantity = (item.quantity + delta).coerceAtLeast(1)
//            cartDao.updateQuantity(cartId, newQuantity)
            val currentItem = cartDao.getCartItemById(cartId, userId)
            if (currentItem != null) {
                val newQuantity = (currentItem.quantity + delta).coerceAtLeast(1)
                cartDao.updateQuantity(cartId, newQuantity)
            }
        }
    }

    private fun deleteItem(cartId: Int) {
        viewModelScope.launch {
            val userId = currentUserId ?: return@launch
//            val item = _state.value.cartItems.find { it.cart_id == cartId } ?: return@launch
//            cartDao.deleteCartItem(
//                CartTable(
//                    cart_id = cartId,
//                    user_id = userId,
//                    product_id = item.product.product_id,
//                    quantity = item.quantity
//                )
//            )
            val item = cartDao.getCartItemById(cartId, userId)
            if (item != null) {
                cartDao.deleteCartItem(item)
            }
        }
    }

    private fun processOrder() {
        viewModelScope.launch {
            val userId = currentUserId ?: return@launch
            val cartItems = _state.value.cartItems

            if (cartItems.isEmpty()) return@launch

            val orderId = orderDao.insertOrder(
                OrderTable(
                    user_id = userId,
                    status = null,
                    estimated_arrival = "3-5 Business Days"
                )
            )

            for (item in cartItems) {
                orderDao.insertOrderItem(
                    OrderItemTable(
                        order_id = orderId.toInt(),
                        product_id = item.product.product_id,
                        quantity = item.quantity,
                        price_at_purchase = item.product.price
                    )
                )
            }

//            cartDao.clearCart(userId)
//
//            _state.value = _state.value.copy(
//                cartItems = emptyList(),
//                itemCount = 0,
//                totalPrice = 0,
//                orderId = orderId.toInt()
//            )
            _state.value = _state.value.copy(orderId = orderId.toInt())
        }
    }

    fun clearLastOrderId() {
        _state.value = _state.value.copy(orderId = null)
    }
}