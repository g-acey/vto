package ord.ibda.vto.ui.myorders

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ord.ibda.vto.R
import ord.ibda.vto.ui.theme.AppTheme

@Composable
fun MyOrdersScreen(
    modifier: Modifier = Modifier
) {
    val tabs = listOf("All", "In progress", "Complete")
    var selectedTabIndex by remember { mutableStateOf(0) }

    val orders = listOf(
        OrderItem(R.drawable.black_tank, "#092312332", "25/05/2025", "Estimated arrival on 28/05/2025", false),
        OrderItem(R.drawable.red_offshoulder, "#092312332", "25/05/2025", "Delivered on 20/05/2025", true)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Top App Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { /* Navigate back */ }
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "Orders",
                style = MaterialTheme.typography.displaySmall
            )
        }

        // Tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
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
                            text = tab,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 16.sp
                            )
                        )
                    },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        // Order List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            itemsIndexed(orders) { index, order ->
                OrderCard(order)
                if (index < orders.lastIndex) {
                    Divider()
                }
            }
        }
    }
}

data class OrderItem(
    val imageRes: Int,
    val orderNumber: String,
    val date: String,
    val statusText: String,
    val delivered: Boolean
)

@Composable
fun OrderCard(order: OrderItem) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  }
            .padding(vertical = 26.dp, horizontal = 20.dp)
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
                painter = painterResource(id = order.imageRes),
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
                text = order.orderNumber,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Text(
                text = order.date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Text(
                text = order.statusText,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = if (order.delivered) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onErrorContainer,
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyOrderScreenPreview() {
    AppTheme {
        MyOrdersScreen()
    }
}