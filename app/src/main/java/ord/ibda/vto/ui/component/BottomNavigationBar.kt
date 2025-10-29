package ord.ibda.vto.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import ord.ibda.vto.R
import ord.ibda.vto.ui.navigation.CartScreenNK
import ord.ibda.vto.ui.navigation.HomeScreenNK

//@Composable
//fun HomeBottomBar(
//    selectedItem: Int,
//    onItemSelected: (Int) -> Unit,
//    cartItemCount: Int = 0
//) {
//    NavigationBar(
//        windowInsets = NavigationBarDefaults.windowInsets,
//        containerColor = MaterialTheme.colorScheme.background,
//        tonalElevation = 6.dp
//    ) {
//        // Home
//        NavigationBarItem(
//            selected = selectedItem == 0,
//            onClick = { onItemSelected(0) },
//            icon = {
//                Icon(
//                    imageVector = if (selectedItem == 0) Icons.Filled.Home else Icons.Outlined.Home,
//                    contentDescription = "Home",
//                    modifier = Modifier.size(30.dp)
//                )
//            },
//            alwaysShowLabel = false,
//            colors = NavigationBarItemDefaults.colors(
//                indicatorColor = Color.Transparent
//            )
//        )
//
//        // Logo (Disabled)
//        NavigationBarItem(
//            selected = false,
//            onClick = {},
//            enabled = false,
//            icon = {
//                Image(
//                    painter = painterResource(R.drawable.valentina_logo),
//                    contentDescription = "Valentina Logo",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .size(28.dp)
//                )
//            }
//        )
//
//        // Cart
//        NavigationBarItem(
//            selected = selectedItem == 2,
//            onClick = { onItemSelected(2) },
//            icon = {
//                BadgedBox(
//                    badge = {
//                        if (cartItemCount > 0) {
//                            Badge {
//                                Text("$cartItemCount")
//                            }
//                        }
//                    }
//                ) {
//                    Icon(
//                        imageVector = if (selectedItem == 2) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
//                        contentDescription = "Cart",
//                        modifier = Modifier.size(30.dp)
//                    )
//                }
//            },
//            alwaysShowLabel = false,
//            colors = NavigationBarItemDefaults.colors(
//                indicatorColor = Color.Transparent
//            )
//        )
//    }
//}

@Composable
fun BottomNavigationBar(
    currentDestination: NavKey,
    onNavigate: (NavKey) -> Unit,
    cartItemCount: Int = 0
) {
    HorizontalDivider(
        modifier = Modifier
            .height(1.dp)
            .background(MaterialTheme.colorScheme.onSurfaceVariant)
    )
    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        tonalElevation = 6.dp,
        modifier = Modifier
            .height(85.dp)
    ) {
        // Home
        NavigationBarItem(
            selected = currentDestination == HomeScreenNK,
            onClick = { onNavigate(HomeScreenNK) },
            icon = {
                Icon(
                    imageVector = if (currentDestination == HomeScreenNK) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(30.dp)
                )
            },
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        // Logo (Disabled)
        NavigationBarItem(
            selected = false,
            onClick = {},
            enabled = false,
            icon = {
                Image(
                    painter = painterResource(R.drawable.valentina_logo),
                    contentDescription = "Valentina Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(28.dp)
                )
            }
        )

        // Cart
        NavigationBarItem(
            selected = currentDestination == CartScreenNK,
            onClick = { onNavigate(CartScreenNK) },
            icon = {
                BadgedBox(
                    badge = {
                        if (cartItemCount > 0) {
                            Badge {
                                Text("$cartItemCount")
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (currentDestination == CartScreenNK) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                        contentDescription = "Cart",
                        modifier = Modifier.size(30.dp)
                    )
                }
            },
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
    }
}