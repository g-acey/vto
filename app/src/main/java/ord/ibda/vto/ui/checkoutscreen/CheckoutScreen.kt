package ord.ibda.vto.ui.checkoutscreen

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ord.ibda.vto.R
import ord.ibda.vto.ui.cartscreen.CartItem
import ord.ibda.vto.ui.theme.AppTheme

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier
) {
    val cartList = listOf(
        CartItem(R.drawable.black_tank, "T12 Tank top", "XS", 2, 499.900),
        CartItem(R.drawable.red_offshoulder, "T12 Tank top", "XS", 1, 499.900)
    )

    Scaffold(
        bottomBar = {
            PayOrder()
        }
    ) { innerPadding ->
        Column {
            CheckoutHeader()
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
                    OrderSummary()
                }
            }
        }
    }
}

@Composable
fun PayOrder(
    modifier: Modifier = Modifier
) {
    Surface(
        tonalElevation = 6.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
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
                    text = "1.049.800 IDR",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.width(50.dp))

            Button(
                onClick = { },
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
                .clickable {  }
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
    modifier: Modifier = Modifier
) {
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

        SummaryRow("Item subtotal", "999.800 IDR")
        SummaryRow("Shipping", "50.000 IDR")
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
fun CartItems(
    modifier: Modifier = Modifier
) {
    val cartList = listOf(
        CartItem(R.drawable.black_tank, "T12 Tank top", "XS", 2, 499.900),
        CartItem(R.drawable.red_offshoulder, "T12 Tank top", "XS", 1, 499.900)
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.padding(horizontal = 20.dp, vertical = 30.dp)
    ) {
        items(cartList) { item ->
            CartItemRow(item)
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem
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
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 150.dp, height = 200.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "${String.format("%.3f", item.price)} IDR",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.size,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (item.quantity > 1) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${item.quantity}x",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${String.format("%.3f", (item.price / item.quantity))} IDR",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    AppTheme {
        CheckoutScreen()
    }
}