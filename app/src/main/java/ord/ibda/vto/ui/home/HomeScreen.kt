package ord.ibda.vto.ui.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ord.ibda.vto.R
import ord.ibda.vto.data.models.ProductType
import ord.ibda.vto.data.models.rooms.ProductTable
import ord.ibda.vto.ui.home.viewmodel.HomeEvent
import ord.ibda.vto.ui.home.viewmodel.HomeViewModel
import ord.ibda.vto.ui.theme.AppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    goProfile: () -> Unit,
    bottomBar: @Composable () -> Unit = {},
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by homeViewModel.state.collectAsState()

//    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        modifier = modifier,
        bottomBar = { bottomBar() }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
        ) {
            TopBar(
                goProfile = { goProfile() }
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalTabRow(
                selectedType = homeState.productType,
                onTabSelected = { type ->
                    homeViewModel.onEvent(HomeEvent.SortProducts(type))
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ProductCatalog(
                products = homeState.products
            )
        }
    }
}

@Composable
fun TopBar(
    goProfile: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
    ) {
        val textFieldState = rememberTextFieldState("")

        val dummyResults = listOf("Dress", "Top", "Jeans", "Shoes")

        SimpleSearchBar(
            textFieldState = textFieldState,
            onSearch = {  },
            searchResults = dummyResults,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.width(12.dp))

        Surface(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            IconButton(
                onClick = { goProfile() },
                modifier = Modifier
                    .height(55.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Icon(
                    Icons.Outlined.Person, contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

//@Composable
//fun HorizontalTabRow(
//    modifier: Modifier = Modifier
//) {
//    val tabs = listOf("All", "Sports", "Teen", "Summer", "Casual")
//    var selectedTabIndex by remember { mutableStateOf(0) }
//
//    ScrollableTabRow(
//        selectedTabIndex = selectedTabIndex,
//        containerColor = Color.Transparent,
//        contentColor = MaterialTheme.colorScheme.onSurface,
//        edgePadding = 20.dp,
//        indicator = { tabPositions ->
//            TabRowDefaults.Indicator(
//                color = MaterialTheme.colorScheme.onSecondaryContainer,
//                modifier = Modifier
//                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
//                    .height(1.dp)
//            )
//        },
//        divider = {},
//        modifier = modifier
//    ) {
//        tabs.forEachIndexed { index, tab ->
//            Tab(
//                text = {
//                    Text(
//                        text = tab,
//                        style = MaterialTheme.typography.labelLarge.copy(
//                            fontSize = 16.sp
//                        ),
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                },
//                selected = selectedTabIndex == index,
//                onClick = { selectedTabIndex = index }
//            )
//        }
//    }
//}

@Composable
fun HorizontalTabRow(
    selectedType: ProductType,
    onTabSelected: (ProductType) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        ProductType.ALL,
        ProductType.SPORTS,
        ProductType.TEEN,
        ProductType.SUMMER,
        ProductType.CASUAL
    )

    var selectedTabIndex = tabs.indexOf(selectedType)

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurface,
        edgePadding = 20.dp,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .height(1.dp)
            )
        },
        divider = {},
        modifier = modifier
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = {
                    Text(
                        text = tab.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}

//data class Category(
//    val imageResId: Int,
//    val label: String
//)
//
//val categories = listOf(
//    Category(R.drawable.see_all, "See all"),
//    Category(R.drawable.sports, "Sports"),
//    Category(R.drawable.teen, "Teen"),
//    Category(R.drawable.summer, "Summer")
//)

//@Composable
//fun HorizontalCategorySection() {
//    Column {
//        Text(
//            "New Collection",
//            style = MaterialTheme.typography.displaySmall,
//            modifier = Modifier.padding(horizontal = 20.dp)
//        )
//        Text(
//            "245 Products",
//            style = MaterialTheme.typography.bodySmall,
//            modifier = Modifier.padding(horizontal = 20.dp)
//        )
//        Spacer(modifier = Modifier.height(14.dp))
//        LazyRow(
//            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(categories) { category ->
//                CategoryCard(
//                    imageResId = category.imageResId,
//                    label = category.label
//                )
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    modifier: Modifier = Modifier
) {
    // Controls expansion state of the search bar
    var expanded by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(start = 20.dp)
            .semantics { traversalIndex = 0f },
        inputField = {
            SearchBarDefaults.InputField(
                query = textFieldState.text.toString(),
                onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                onSearch = {
                    onSearch(textFieldState.text.toString())
                    expanded = false
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search, contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    ) },
                trailingIcon = {
                    if (expanded) {
                        IconButton(onClick = {  }) {
                            Icon(
                                Icons.Default.Close, contentDescription = "Clear",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Default.Tune, contentDescription = "Filter",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        },
        colors = SearchBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            dividerColor = MaterialTheme.colorScheme.outlineVariant
        ),
        expanded = expanded,
        shape = RoundedCornerShape(4.dp),
        onExpandedChange = { expanded = it },
    ) {
        // Display search results in a scrollable column
//            Column(Modifier.verticalScroll(rememberScrollState())) {
//                searchResults.forEach { result ->
//                    ListItem(
//                        headlineContent = { Text(result) },
//                        modifier = Modifier
//                            .clickable {
//                                textFieldState.edit { replace(0, length, result) }
//                                expanded = false
//                            }
//                            .fillMaxWidth()
//                    )
//                }
//            }
    }
}


//@Composable
//fun CategoryCard(
//    imageResId: Int,
//    label: String,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        horizontalAlignment = Alignment.Start,
//        modifier = modifier
//            .fillMaxWidth()
//    ) {
//        Image(
//            painter = painterResource(id = imageResId),
//            contentDescription = label,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .width(100.dp)
//                .height(140.dp)
//                .background(MaterialTheme.colorScheme.surfaceVariant)
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        Text(
//            text = label,
//            style = MaterialTheme.typography.labelMedium,
//            textAlign = TextAlign.Center,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis
//        )
//    }
//}

@Composable
fun ProductCatalog(
    products: List<ProductTable>,
    modifier: Modifier = Modifier
) {
//    val products = listOf(
//        Product(R.drawable.flowy_white_shirt, "D23 Ripped denim shorts", "499.900 IDR"),
//        Product(R.drawable.flowy_pattern_shirt, "D22 Denim shorts", "599.900 IDR"),
//        Product(R.drawable.flowy_pattern_shirt, "D23 Ripped denim shorts", "499.900 IDR"),
//        Product(R.drawable.flowy_white_shirt, "D22 Denim shorts", "599.900 IDR")
//    )
    if (products.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No products available yet.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
//            item(span = { GridItemSpan(2) }) {
//                BigProductCard(
//                    product = products.first(),
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }

            // Two-column product grid
            items(products) { product ->
                ProductItem(product)
            }
        }
    }
}

//@Composable
//fun BigProductCard(
//    product: ProductTable,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        horizontalAlignment = Alignment.Start,
//        modifier = modifier
//            .fillMaxWidth()
//    ) {
////        Image(
////            painter = painterResource(id = R.drawable.preview),
////            contentDescription = "Short flowing printed dress",
////            contentScale = ContentScale.Crop,
////            modifier = Modifier
////                .fillMaxWidth()
////                .height(180.dp)
////                .background(MaterialTheme.colorScheme.surfaceVariant)
////        )
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(product.product_image)
//                .crossfade(true)
//                .build(),
//            contentDescription = product.product_name,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(180.dp)
//                .background(MaterialTheme.colorScheme.surfaceVariant)
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        Text(
//            text = product.product_name,
//            style = MaterialTheme.typography.bodySmall,
//            textAlign = TextAlign.Center,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis
//        )
//        Text(
//            text = "${product.price} IDR",
//            style = MaterialTheme.typography.titleMedium,
//            textAlign = TextAlign.Center,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis
//        )
//    }
//}

//data class Product(
//    val imageRes: Int,
//    val name: String,
//    val price: String
//)

@Composable
fun ProductItem(
    product: ProductTable
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
//        Image(
//            painter = painterResource(id = product.imageRes),
//            contentDescription = product.name,
//            modifier = Modifier
//                .fillMaxWidth()
//                .aspectRatio(1f),
//            contentScale = ContentScale.Crop
//        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.product_image)
                .crossfade(true)
                .build(),
            contentDescription = product.product_name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.product_name,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "${product.price} IDR",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(goProfile = {})
    }
}