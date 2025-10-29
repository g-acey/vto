package ord.ibda.vto.ui.myorder

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ord.ibda.vto.data.models.FullOrderWithDetails
import ord.ibda.vto.data.models.OrderStatus
import ord.ibda.vto.ui.myorder.viewmodel.MyOrderEvent
import ord.ibda.vto.ui.myorder.viewmodel.MyOrderViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MyOrdersScreen(
    modifier: Modifier = Modifier,
    myOrderViewModel: MyOrderViewModel = hiltViewModel(),
    onBack: () -> Unit,
    goOrderDetails: (Int) -> Unit,
) {
    val myOrderState by myOrderViewModel.state.collectAsState()

    val tabs = listOf(OrderStatus.ALL, OrderStatus.IN_PROGRESS, OrderStatus.COMPLETE)
    var selectedTabIndex = tabs.indexOf(myOrderState.orderStatus)

//    val orders = listOf(
//        OrderItem(R.drawable.black_tank, "#092312332", "25/05/2025", "Estimated arrival on 28/05/2025", false),
//        OrderItem(R.drawable.red_offshoulder, "#092312332", "25/05/2025", "Delivered on 20/05/2025", true)
//    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 25.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onBack() }
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "Orders",
                style = MaterialTheme.typography.displaySmall
            )
        }

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(1.dp)
                )
            },
            divider = {},
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = {
                        Text(
                            text = tab.name.replace("_", " ").lowercase()
                                .replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 16.sp
                            )
                        )
                    },
                    selected = selectedTabIndex == index,
                    onClick = { myOrderViewModel.onEvent(MyOrderEvent.SortOrders(tab)) }
                )
            }
        }

        if (myOrderState.orders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No orders found",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(myOrderState.orders) { index, order ->
                    OrderCard(
                        order = order,
                        onClick = { goOrderDetails(order.order.order_id) }
                    )
                    if (index < myOrderState.orders.lastIndex) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

//data class OrderItem(
//    val imageRes: Int,
//    val orderNumber: String,
//    val date: String,
//    val statusText: String,
//    val delivered: Boolean
//)

@Composable
fun OrderCard(
    order: FullOrderWithDetails,
    onClick: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val orderDate = dateFormat.format(Date(order.order.order_date))

    val statusText = when (order.order.status) {
        "In Progress" -> "Estimated arrival: ${order.order.estimated_arrival ?: "3-5 business days"}"
        "Complete" -> "Delivered"
        else -> "Unknown"
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 26.dp, horizontal = 20.dp)
    ) {
//        Box(
//            modifier = Modifier
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(width = 150.dp, height = 200.dp)
//                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
//            ) {
//
//            }
//            Image(
//                painter = painterResource(id = order.imageRes),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(width = 150.dp, height = 200.dp)
//            )
//        }
        Box(
            modifier = Modifier
                .size(width = 150.dp, height = 200.dp)
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
        ) {
            val firstItem = order.items.firstOrNull()
            if (firstItem != null) {
//                Image(
//                    painter = rememberAsyncImagePainter(firstItem.product.image_url),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(firstItem.product.product_image)
                        .crossfade(true)
                        .build(),
                    contentDescription = firstItem.product.product_name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "#${order.order.order_id}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Text(
                text = orderDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Text(
                text = statusText,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = if (order.order.status == "Complete") MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onErrorContainer,
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MyOrderScreenPreview() {
//    AppTheme {
//        MyOrdersScreen(onBack = {})
//    }
//}