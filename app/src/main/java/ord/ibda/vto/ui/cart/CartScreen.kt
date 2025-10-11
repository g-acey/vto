package ord.ibda.vto.ui.cart

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ord.ibda.vto.data.models.CartProductDetail
import ord.ibda.vto.ui.cart.viewmodel.CartEvent
import ord.ibda.vto.ui.cart.viewmodel.CartViewModel
import ord.ibda.vto.ui.theme.AppTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    goCheckoutScreen: (Int) -> Unit,
    bottomBar: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val cartState by cartViewModel.state.collectAsState()

    LaunchedEffect(cartState.orderId) {
        cartState.orderId?.let { orderId ->
            goCheckoutScreen(orderId)
            cartViewModel.clearLastOrderId()
        }
    }

    Scaffold(
        bottomBar = {
            Column {
                ProcessOrder(
                    totalPrice = cartState.totalPrice,
                    onProcessOrder = { cartViewModel.onEvent(CartEvent.ProcessOrder) }
                )
                bottomBar()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(top = 25.dp)
        ) {
            CartTitle(
                itemCount = cartState.itemCount,
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            )
            when {
                cartState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                cartState.cartItems.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Your cart is empty.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {
                    CartItems(
                        cartList = cartState.cartItems,
                        onDecrease = { cartViewModel.onEvent(CartEvent.DecreaseQuantity(it)) },
                        onIncrease = { cartViewModel.onEvent(CartEvent.IncreaseQuantity(it)) },
                        onDelete = { cartViewModel.onEvent(CartEvent.DeleteItem(it)) },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CartTitle(
    itemCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Cart",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .weight(1.2f)
        )
        Text(
            text = "(${itemCount})",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(3f)
                .padding(top = 10.dp)
        )
    }
}

@Composable
fun CartItems(
    cartList: List<CartProductDetail>,
    onDecrease: (Int) -> Unit,
    onIncrease: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (cartList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Your cart is empty",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            items(cartList) { item ->
                CartItemRow(
                    item = item,
                    onDecrease = { onDecrease(item.cart_id) },
                    onIncrease = { onIncrease(item.cart_id) },
                    onDelete = { onDelete(item.cart_id) }
                )
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartProductDetail,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    onDelete: () -> Unit
) {
    val product = item.product

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
                    .data(product.product_image)
                    .crossfade(true)
                    .build(),
                contentDescription = product.product_name,
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

        val formattedPrice = NumberFormat.getNumberInstance(Locale("in", "ID"))
            .format(product.price)

        val formattedTotalPrice = NumberFormat.getNumberInstance(Locale("in", "ID"))
            .format((product.price * item.quantity))

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .weight(1f)
                .height(200.dp)
        ) {
            Column {
                Text(
                    text = "$formattedTotalPrice IDR",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
                Text(
                    text = product.product_name,
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
                            text = "$formattedPrice IDR",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceContainerHigh,
                        RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                IconButton(
                    onClick = onDecrease,
                    enabled = item.quantity > 1,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Decrease quantity",
                        tint = if (item.quantity > 1)
                            MaterialTheme.colorScheme.onSurface
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                    )
                }
                Text(
                    text = item.quantity.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(
                    onClick = onIncrease,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase quantity",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Top)
        ) {
            Icon(
                Icons.Outlined.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun TotalDetails(
    totalPrice: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
        Text(
            text = "Total",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(top = 5.dp)
                .weight(0.7f)
        )
        Text(
            text = "(VAT Included)",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = "${"%,d".format(totalPrice)} IDR",
            style = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.End
            ),
            modifier = Modifier
                .padding(top = 5.dp)
                .weight(2f)
        )
    }
}

@Composable
fun ProcessOrder(
    totalPrice: Int,
    onProcessOrder: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        tonalElevation = 6.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            TotalDetails(
                totalPrice = totalPrice,
                modifier = Modifier
                    .padding(bottom = 30.dp)
            )
            Button(
                onClick = { onProcessOrder() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
            ) {
                Text(
                    text = "Process Order",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    AppTheme {
//        CartScreen(goCheckoutScreen = {}, cartViewModel = )
    }
}