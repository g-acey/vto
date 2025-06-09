package ord.ibda.vto.ui.homescreen

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ord.ibda.vto.ui.theme.AppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {

    }
}

@Composable
fun BodyScrollable(
    modifier: Modifier = Modifier
) {

}

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier
) {

}

@Composable
fun FilterSlider(
    modifier: Modifier = Modifier
) {

}

@Composable
fun ProductShowcaseBig(
    modifier: Modifier = Modifier
) {

}

@Composable
fun ProductShowcase(
    modifier: Modifier = Modifier
) {

}

@Composable
fun SearchBar() {
    SearchBar(
        
    )
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        SearchBar()
    }
}