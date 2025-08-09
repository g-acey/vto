package ord.ibda.vto.ui.productdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CenterFocusWeak
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ord.ibda.vto.R
import ord.ibda.vto.ui.theme.AppTheme
import ord.ibda.vto.ui.theme.fontFamilySemiBold

@Composable
fun ProductDetailsScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.product_detail_preview),
//            contentDescription = "Product image",
//            modifier = Modifier
//                .fillMaxSize()
//                .aspectRatio(1 / 2f)
//                .graphicsLayer(scaleX = 1.2f, scaleY = 1.2f)
//                .absoluteOffset(x = 0.dp, y = (-75).dp)
//        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
        )   {
//            FloatingActionButton(
//                onClick = { },
//                shape = RoundedCornerShape(4.dp),
//                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
//                contentColor = MaterialTheme.colorScheme.onSurface,
//                modifier = Modifier
//                    .align(alignment = Alignment.End)
//                    .padding(16.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Rounded.CenterFocusWeak,
//                    contentDescription = "Virtual clothes try-on icon",
//                    modifier = Modifier
//                        .size(30.dp)
//                )
//            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f)
            ) {
                PartialBottomSheet()
            }
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .weight(1f)
            ) {
                Button(
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp)
                ) {
                    Text(
                        text = "Select Size",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun ProductTitle(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Camisole with mid-ribbon",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "399.900 IDR",
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
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Top with cowl neckline and thin straps that adjust at the back." +
                        " Gathered fabric detail on the sides.",
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
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Column(
            modifier = Modifier
        ) {
            ProductDescription()
            AboutProduct()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(
    modifier: Modifier = Modifier
) {

    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BsContent()
        },
        sheetPeekHeight = 100.dp,
        sheetShape = RoundedCornerShape(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Image(
                painter = painterResource(id = R.drawable.teen),
                contentDescription = "Product image",
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1 / 2f)
                    .graphicsLayer(scaleX = 1.35f, scaleY = 1.35f)
                    .absoluteOffset(x = 0.dp, y = (-75).dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(670.dp)
            ) {
                FloatingActionButton(
                    onClick = { },
                    shape = RoundedCornerShape(4.dp),
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CenterFocusWeak,
                        contentDescription = "Virtual clothes try-on icon",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsScreenPreview() {
    AppTheme {
        ProductDetailsScreen()
    }
}