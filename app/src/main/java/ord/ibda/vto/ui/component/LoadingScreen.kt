package ord.ibda.vto.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ord.ibda.vto.R
import ord.ibda.vto.ui.theme.AppTheme

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
//        Image(
//            painter = painterResource(R.drawable.valentina_logo),
//            contentDescription = "Valentina Logo",
//            modifier = Modifier
//                .aspectRatio(1f)
//        )
        Spacer(modifier = Modifier.height(16.dp))
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.outlineVariant,
            modifier = Modifier
                .size(45.dp)
//                .absoluteOffset(y = 100.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    AppTheme {
        LoadingScreen()
    }
}