package ord.ibda.vto.ui.productdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.CenterFocusWeak
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ord.ibda.vto.R
import ord.ibda.vto.data.models.rooms.ProductTable
import ord.ibda.vto.ui.productdetails.viewmodel.ProductDetailsEvent
import ord.ibda.vto.ui.productdetails.viewmodel.ProductDetailsViewModel
import ord.ibda.vto.ui.theme.AppTheme
import ord.ibda.vto.ui.theme.fontFamilySemiBold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    productId: Int,
    onBack: () -> Unit,
    onGoToCart: () -> Unit,
    onTryOnClick: (Int) -> Unit,
    productDetailsViewModel: ProductDetailsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val productDetailsState by productDetailsViewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(productId) {
        productDetailsViewModel.onEvent(ProductDetailsEvent.LoadProduct(productId))
    }

    LaunchedEffect(productDetailsState.showSnackbar) {
        if (productDetailsState.showSnackbar) {
            val result = snackbarHostState.showSnackbar(
                message = "Product added",
                actionLabel = "Go to cart",
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.ActionPerformed) {
                onGoToCart()
            }
            productDetailsViewModel.onSnackbarShown()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        productDetailsState.product?.let { product ->
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(8f)
                ) {
                    PartialBottomSheet(
                        onBack = onBack,
                        product = product,
                        onTryOnClick = onTryOnClick
                    )
                }

                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .weight(1f)
                ) {
                    Button(
                        onClick = {
                            productDetailsViewModel.onEvent(
                                ProductDetailsEvent.AddToCart(product.product_id)
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp)
                    ) {
                        Text(
                            text = "Add to cart",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        } ?: run {
            Text(
                text = if (productDetailsState.isLoading)
                    "Loading..."
                else productDetailsState.error ?: "Product not found",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 26.5.dp)
        ) { data ->
            Snackbar(
                shape = RoundedCornerShape(4.dp),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(53.dp)
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(4.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.visuals.message,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { data.performAction() }
                            .padding(start = 12.dp)
                    ) {
                        Text(
                            text = data.visuals.actionLabel ?: "",
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            imageVector = Icons.Outlined.ArrowForward,
                            contentDescription = "Go to cart",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductTitle(
    product: ProductTable,
    modifier: Modifier = Modifier
) {
    val formattedPrice = remember(product.price) {
        "%,d IDR".format(product.price).replace(',', '.')
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = product.product_name,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = formattedPrice,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 20.sp,
                fontFamily = fontFamilySemiBold
            ),
        )
    }
}

@Composable
fun ProductDescription(
    product: ProductTable,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ProductTitle(
                product = product,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
            Text(
                text = product.product_details,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Justify
                )
            )
        }
    }
}

@Composable
fun ExpandableSectionTitle(
    isExpanded: Boolean,
    title: String,
    modifier: Modifier = Modifier
) {
    val icon = if (isExpanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .weight(1f)
        )

        Icon(
            imageVector = icon,
            contentDescription = if (isExpanded) "Collapse" else "Expand",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ExpandableSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier
            .clickable { isExpanded = !isExpanded }
            .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        ExpandableSectionTitle(isExpanded = isExpanded, title = title)

        AnimatedVisibility(
            visible = isExpanded,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .fillMaxWidth()
        ) {
            content()
        }
        Divider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp,
        )
    }
}

@Composable
fun AboutProduct(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Text(
            text = "About this product",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Divider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        ExpandableSection(
            title = stringResource(R.string.about_product_title1),
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.about_product_description1),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall.copy(
                    textAlign = TextAlign.Justify
                ),
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )
        }
        ExpandableSection(
            title = stringResource(R.string.about_product_title2),
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.about_product_description2),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall.copy(
                    textAlign = TextAlign.Justify
                ),
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )
        }
        ExpandableSection(
            title = stringResource(R.string.about_product_title3),
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.about_product_description3),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall.copy(
                    textAlign = TextAlign.Justify
                ),
                modifier = Modifier
                    .padding(vertical = 12.dp)
            )
        }
    }
}

@Composable
fun ProductRecommendation(
    modifier: Modifier = Modifier
) {

}

@Composable
fun BsContent(
    product: ProductTable,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Column(
            modifier = Modifier
        ) {
            ProductDescription(
                product = product
            )
            AboutProduct()
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PartialBottomSheet(
//    onBack: () -> Unit,
//    product: ProductTable,
//    onTryOnClick: (Int) -> Unit,
//    modifier: Modifier = Modifier
//) {
//
//    val scaffoldState = rememberBottomSheetScaffoldState()
//
//    BottomSheetScaffold(
//        scaffoldState = scaffoldState,
//        sheetContent = {
//            BsContent(
//                product = product
//            )
//        },
//        sheetPeekHeight = 100.dp,
//        sheetShape = RoundedCornerShape(0.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = MaterialTheme.colorScheme.surfaceVariant)
//        ) {
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(product.product_image)
//                    .crossfade(true)
//                    .build(),
//                contentDescription = product.product_name,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .aspectRatio(1 / 2f)
//                    .absoluteOffset(x = 0.dp, y = (-25).dp)
//            )
////            Image(
////                painter = painterResource(id = R.drawable.teen),
////                contentDescription = "Product image",
////                modifier = Modifier
////                    .fillMaxSize()
////                    .aspectRatio(1 / 2f)
////                    .graphicsLayer(scaleX = 1.35f, scaleY = 1.35f)
////                    .absoluteOffset(x = 0.dp, y = (-75).dp)
////            )
//            Box(
//                modifier = Modifier
//                    .align(Alignment.TopStart)
//                    .padding(top = 32.dp, start = 16.dp)
//                    .size(40.dp)
//                    .clip(RoundedCornerShape(4.dp))
//                    .clickable(
//                        interactionSource = remember { MutableInteractionSource() },
//                        indication = LocalIndication.current
//                    ) {
//                        onBack()
//                    },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Default.ArrowBack,
//                    contentDescription = "Back",
//                    tint = MaterialTheme.colorScheme.onSurface,
//                    modifier = Modifier
//                        .size(32.dp)
//                )
//            }
////            Box(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .height(670.dp)
////            ) {
////                FloatingActionButton(
////                    onClick = { onTryOnClick(product.product_id) },
////                    shape = RoundedCornerShape(4.dp),
////                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
////                    contentColor = MaterialTheme.colorScheme.onSurface,
////                    modifier = Modifier
////                        .align(Alignment.BottomEnd)
////                        .padding(horizontal = 20.dp, vertical = 12.dp)
////                ) {
////                    Icon(
////                        imageVector = Icons.Rounded.CenterFocusWeak,
////                        contentDescription = "Virtual clothes try-on icon",
////                        modifier = Modifier
////                            .size(30.dp)
////                    )
////                }
////            }
//            FloatingActionButton(
//                onClick = { onTryOnClick(product.product_id) },
//                shape = RoundedCornerShape(4.dp),
//                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
//                contentColor = MaterialTheme.colorScheme.onSurface,
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(horizontal = 20.dp, vertical = 20.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Rounded.CenterFocusWeak,
//                    contentDescription = "Virtual clothes try-on icon",
//                    modifier = Modifier.size(30.dp)
//                )
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(
    onBack: () -> Unit,
    product: ProductTable,
    onTryOnClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BsContent(product = product)
        },
        sheetPeekHeight = 100.dp,
        sheetShape = RoundedCornerShape(0.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            // --- Product image (bottom layer) ---
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.product_image)
                    .crossfade(true)
                    .build(),
                contentDescription = product.product_name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1 / 2f)
                    .absoluteOffset(x = 0.dp, y = (-25).dp)
                    .zIndex(0f)
            )

            // --- FAB (middle layer: above image, below sheet) ---
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 120.dp) // same as your sheetPeekHeight or a bit more
                    .padding(end = 20.dp)
                    .zIndex(1f),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(
                    onClick = { onTryOnClick(product.product_id) },
                    shape = RoundedCornerShape(4.dp),
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CenterFocusWeak,
                        contentDescription = "Virtual clothes try-on icon",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            // --- Back button (top layer, above everything) ---
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 32.dp, start = 16.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = LocalIndication.current
                    ) {
                        onBack()
                    }
                    .zIndex(2f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}




//@Preview(showBackground = true)
//@Composable
//fun ProductDetailsScreenPreview() {
//    AppTheme {
//        ProductDetailsScreen(productId = 0, onBack = {}, onGoToCart = {})
//    }
//}