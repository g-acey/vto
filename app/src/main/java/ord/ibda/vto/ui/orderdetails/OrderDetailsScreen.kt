package ord.ibda.vto.ui.orderdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ord.ibda.vto.ui.checkout.AddressColumn
import ord.ibda.vto.ui.checkout.CartItemRow
import ord.ibda.vto.ui.checkout.CheckoutHeader
import ord.ibda.vto.ui.checkout.PaymentColumn
import ord.ibda.vto.ui.checkout.PromoColumn
import ord.ibda.vto.ui.checkout.SummaryRow
import ord.ibda.vto.ui.checkout.viewmodel.CheckoutEvent
import ord.ibda.vto.ui.component.LoadingScreen
import ord.ibda.vto.ui.orderdetails.viewmodel.OrderDetailsEvent
import ord.ibda.vto.ui.orderdetails.viewmodel.OrderDetailsViewModel
import ord.ibda.vto.ui.theme.AppTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun OrderDetailsScreen(
    orderId: Int,
    onBack: () -> Unit,
    orderDetailsViewModel: OrderDetailsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val orderDetailsState by orderDetailsViewModel.state.collectAsState()

    LaunchedEffect(orderId) {
        orderDetailsViewModel.onEvent(OrderDetailsEvent.LoadOrder(orderId))
    }

    if (orderDetailsState.isLoading) {
        LoadingScreen()
    } else {
        Scaffold(
            modifier = Modifier
                .padding(top = 25.dp)
        ) { innerPadding ->
            Column {
                CheckoutHeader(
                    title = "#${orderDetailsState.order?.order_id ?: ""}",
                    showConfirmation = onBack
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    item { AddressColumn() }
                    item { Divider() }

                    item { PaymentColumn() }
                    item { Divider() }

                    orderDetailsState.items?.let { items ->
                        items(items) { item ->
                            CartItemRow(item)
                        }
                    }

                    item { Divider() }

                    item { PromoColumn() }
                    item { Divider() }

                    item {
                        OrderSummary(
                            subtotal = orderDetailsState.subtotal,
                            shippingCost = orderDetailsState.shippingCost,
                            total = orderDetailsState.totalPrice
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderSummary(
    subtotal: Int,
    shippingCost: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    val subtotalFormattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID"))
        .format(subtotal)

    val shippingCostFormattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID"))
        .format(shippingCost)

    val totalFormattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID"))
        .format(total)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Order Summary",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(12.dp))

        SummaryRow("Item subtotal", "$subtotalFormattedPrice IDR")
        SummaryRow("Shipping", "$shippingCostFormattedPrice IDR")
        SummaryRow("VAT", "Included", valueStyle = MaterialTheme.typography.bodySmall)
        SummaryRow("Total", "$totalFormattedPrice IDR")
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CheckoutScreenPreview() {
//    AppTheme {
//        OrderDetailsScreen()
//    }
//}