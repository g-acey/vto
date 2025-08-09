package ord.ibda.vto.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ord.ibda.vto.R
import ord.ibda.vto.ui.theme.AppTheme

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    goLogin: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Image(
            painter = painterResource(id = R.drawable.home_asset),
            contentDescription = "Welcome image",
            modifier = Modifier
                .absoluteOffset(x = 175.dp, y = 500.dp)
                .graphicsLayer(scaleX = 2.3f, scaleY = 2.3f)
        )
        Text(
            text = stringResource(R.string.welcome_message),
            style = MaterialTheme.typography.displayLarge.copy(
                lineHeight = 80.sp
            ),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 80.sp,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 60.dp)
        )
        Button(
            onClick = { goLogin() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 60.dp, horizontal = 20.dp)
                .fillMaxWidth()
                .height(53.dp)
        ) {
            Text(
                text = "Go shopping",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    AppTheme {
        WelcomeScreen(goLogin = {})
    }
}