package ord.ibda.vto.ui.checkout

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.NavigateNext
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ord.ibda.vto.R
import ord.ibda.vto.data.models.OrderProductDetail
import ord.ibda.vto.ui.checkout.viewmodel.CheckoutEvent
import ord.ibda.vto.ui.checkout.viewmodel.CheckoutViewModel
import ord.ibda.vto.ui.theme.AppTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CheckoutScreen(
    orderId: Int,
    gobacktoCart: () -> Unit,
    goHome: () -> Unit,
    checkoutViewModel: CheckoutViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val checkoutState by checkoutViewModel.state.collectAsState()
//    val snackbarHostState = remember { SnackbarHostState() }

//    LaunchedEffect(checkoutState.snackbarMessage) {
//        checkoutState.snackbarMessage?.let { message ->
//            snackbarHostState.showSnackbar(message)
//            checkoutViewModel.onEvent(CheckoutEvent.ClearSnackbar)
//        }
//    }

    LaunchedEffect(orderId) {
        checkoutViewModel.onEvent(CheckoutEvent.LoadOrder(orderId))
    }

    val cartList = checkoutState.orderItems

    Scaffold(
//        snackbarHost = {
//            SnackbarHost(
//                hostState = snackbarHostState,
//                modifier = Modifier
//                    .padding(bottom = 26.5.dp)
//            ) { data ->
//                Snackbar(
//                    shape = RoundedCornerShape(4.dp),
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp)
//                        .shadow(8.dp, RoundedCornerShape(8.dp))
//                ) {
//                    Box(
//                        modifier = Modifier.fillMaxWidth(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = data.visuals.message,
//                            style = MaterialTheme.typography.bodyMedium.copy(
//                                fontWeight = FontWeight.Medium
//                            )
//                        )
//                    }
//                }
//            }
//        },
        bottomBar = {
            PayOrder(
                totalPrice = checkoutState.totalPrice,
                payOrder = {
                    checkoutViewModel.onEvent(CheckoutEvent.PayOrder)
                    goHome()
                }
            )
        }
    ) { innerPadding ->
        Column {
            CheckoutHeader(
                showConfirmation = {
                    checkoutViewModel.onEvent(CheckoutEvent.ShowConfirmation)
                }
            )
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                item {
                    AddressColumn(
                        modifier = Modifier
                            .clickable {  }
                    )
                }
                item { Divider() }

                item {
                    PaymentColumn(
                        modifier = Modifier
                            .clickable {  }
                    )
                }
                item { Divider() }

                items(cartList) { item ->
                    CartItemRow(item)
                }

                item { Divider() }

                item {
                    PromoColumn(
                        modifier = Modifier
                            .clickable {  }
                    )
                }
                item { Divider() }

                item {
                    OrderSummary(
                        subtotal = checkoutState.subtotal,
                        shippingCost = checkoutState.shippingCost
                    )
                }
            }
        }
        if (checkoutState.showConfirmationDialog) {
            CancelOrderDialog(
                onConfirm = {
                    checkoutViewModel.onEvent(CheckoutEvent.HideConfirmation)
                },
                onDismiss = {
                    checkoutViewModel.onEvent(CheckoutEvent.DeleteOrder)
                    gobacktoCart()
                }
            )
        }
    }
}

@Composable
fun PayOrder(
    payOrder: () -> Unit,
    totalPrice: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        tonalElevation = 6.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = modifier
            .fillMaxWidth()
            .height(105.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Column {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${"%,d".format(totalPrice)} IDR",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.width(50.dp))

            Button(
                onClick = { payOrder() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
            ) {
                Text(
                    text = "Pay",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Composable
fun CheckoutHeader(
    showConfirmation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(32.dp)
                .clickable { showConfirmation() }
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = "Checkout",
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Composable
fun AddressColumn(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Grace Abrams",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Jl. Oojok Kuning Blok A11 no. 17,\nPanjang Tanjung, Jakarta Utara,\nDKI Jakarta, 13422",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(
            Icons.Outlined.NavigateNext, contentDescription = "Next",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun PaymentColumn(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.mastercard_logo),
            contentDescription = "MasterCard",
            modifier = Modifier
                .size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Master card ending ****90",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .weight(1f)
        )
        Icon(
            Icons.Outlined.NavigateNext, contentDescription = "Next",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun PromoColumn(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.AddCircleOutline,
            contentDescription = "Add Promo",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(18.dp))
        Text(
            text = "Add promo code",
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun OrderSummary(
    subtotal: Int,
    shippingCost: Int,
    modifier: Modifier = Modifier
) {
    val subtotalFormattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID"))
        .format(subtotal)

    val shippingCostFormattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID"))
        .format(shippingCost)

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
    }
}

@Composable
fun SummaryRow(
    label: String,
    value: String,
    valueStyle: TextStyle = MaterialTheme.typography.labelMedium
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = value,
            style = valueStyle,
        )
    }
}

@Composable
fun CartItemRow(
    item: OrderProductDetail
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
        ) {
            Box(
                modifier = Modifier
                    .size(width = 150.dp, height = 200.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            ) {

            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.product.product_image)
                    .crossfade(true)
                    .build(),
                contentDescription = item.product.product_name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 150.dp, height = 200.dp)
            )
//            Image(
//                painter = painterResource(id = item.imageRes),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(width = 150.dp, height = 200.dp)
//            )
        }

        val totalPriceFormattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID"))
            .format(item.price_at_purchase * item.quantity)

        val priceFormattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID"))
            .format(item.price_at_purchase)

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "$totalPriceFormattedPrice IDR",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Text(
                text = item.product.product_name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (item.quantity > 1) {
                    Text(
                        text = "${item.quantity}x",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$priceFormattedPrice IDR",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}

@Composable
fun CancelOrderDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Finish payment?",
                style = MaterialTheme.typography.headlineSmall, // more prominent title
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Text(
                text = "Are you sure you want to go back? This will cancel your payment process.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(4.dp),
//                modifier = Modifier
//                    .width(125.dp)
            ) {
                Text("Continue payment")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                border = ButtonDefaults.outlinedButtonBorder,
                shape = RoundedCornerShape(4.dp),
//                modifier = Modifier
//                    .width(75.dp)
            ) {
                Text(
                    text = "Leave",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(4.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    AppTheme {
        CheckoutScreen(orderId = 0, gobacktoCart = {}, goHome = {})
//        CancelOrderDialog(onConfirm = {}, onDismiss = {})
    }
}