package ord.ibda.vto.ui.cartscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ord.ibda.vto.R
import ord.ibda.vto.ui.theme.AppTheme

@Composable
fun CartScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            ProcessOrder()
        }
    ) { innerPadding ->
        Column {
            CartTile(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            CartItems(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}

@Composable
fun CartTile(
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
            text = "(3)",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(3f)
                .padding(top = 10.dp)
        )
    }
}

data class CartItem(
    val imageRes: Int,
    val name: String,
    val size: String,
    val quantity: Int,
    val price: Double
)

@Composable
fun CartItems(
    modifier: Modifier = Modifier
) {
    val cartList = listOf(
        CartItem(R.drawable.black_tank, "T12 Tank top", "XS", 2, 499.900),
        CartItem(R.drawable.red_offshoulder, "T12 Tank top", "XS", 1, 499.900),
        CartItem(R.drawable.red_offshoulder, "T12 Tank top", "XS", 1, 499.900),
        CartItem(R.drawable.red_offshoulder, "T12 Tank top", "XS", 1, 499.900)
    )

    LazyColumn(
        modifier = modifier
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

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .height(20.dp)
        ) {
            IconButton(
                onClick = { /* edit action */ },
                modifier = Modifier
                    .size(17.dp)
            ) {
                Icon(
                    Icons.Outlined.Edit, contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Divider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(
                onClick = { /* delete action */ },
                modifier = Modifier
                    .size(17.dp)
            ) {
                Icon(
                    Icons.Outlined.Delete, contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun TotalDetails(
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
            text = "999.800 IDR",
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
    modifier: Modifier = Modifier
) {
    Surface(
        tonalElevation = 6.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(horizontal = 20.dp)
        ) {
            TotalDetails(
                modifier = Modifier
                    .padding(bottom = 30.dp)
            )
            Button(
                onClick = { },
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
        CartScreen()
    }
}