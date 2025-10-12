package ord.ibda.vto.ui.myorder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ord.ibda.vto.data.db.OrderDao
import ord.ibda.vto.data.models.FullOrderWithDetails
import ord.ibda.vto.data.models.OrderStatus
import ord.ibda.vto.data.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class MyOrderViewModel @Inject constructor(
    private val orderDao: OrderDao,
    private val sessionManager: SessionManager
): ViewModel() {

    private val _orderStatus = MutableStateFlow(OrderStatus.ALL)
    private val _orders = _orderStatus
        .flatMapLatest { status ->
            flow {
                val userId = sessionManager.loggedInUserId.firstOrNull() ?: return@flow
                val baseOrders = when (status) {
                    OrderStatus.ALL -> orderDao.getAllOrdersWithItems(userId)
                    OrderStatus.IN_PROGRESS -> orderDao.getInProgressOrdersWithItems(userId)
                    OrderStatus.COMPLETE -> orderDao.getCompletedOrdersWithItems(userId)
                }

                val detailedOrders = baseOrders.map { orderWithItems ->
                    val productDetails = orderDao.getOrderProductDetails(orderWithItems.order.order_id)
                    FullOrderWithDetails(orderWithItems.order, productDetails)
                }

                emit(detailedOrders)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(MyOrderState())
    val state = combine(_state, _orderStatus, _orders) { state, orderStatus, orders ->
        state.copy(
            orders = orders,
            orderStatus = orderStatus
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MyOrderState())

    init {
        viewModelScope.launch {
            orderDao.deleteAbandonedOrders()
        }

        viewModelScope.launch {
            while (true) {
                updateExpiredOrders()
                delay(60_000L)
            }
        }
    }

    private suspend fun updateExpiredOrders() {
        val userId = sessionManager.loggedInUserId.firstOrNull() ?: return
        val allOrders = orderDao.getAllOrdersWithItems(userId)
        val now = System.currentTimeMillis()
        val threeDays = 3 * 24 * 60 * 60 * 1000L

        for (order in allOrders) {
            val date = order.order.order_date
            if (order.order.status == "In Progress" && now - date >= threeDays) {
                orderDao.updateOrderStatus(order.order.order_id, "Complete")
            }
        }
    }

    fun onEvent(event: MyOrderEvent) {
        when(event) {
            is MyOrderEvent.SortOrders -> {
                _orderStatus.value = event.orderStatus
            }
        }
    }
}