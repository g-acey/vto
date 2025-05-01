package ord.ibda.vto.ui.loginscreen

import androidx.annotation.Nullable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import okhttp3.internal.http2.FlowControlListener
import ord.ibda.vto.Greeting
import ord.ibda.vto.R
import ord.ibda.vto.ui.theme.AppTheme
import ord.ibda.vto.ui.theme.fontFamily
import ord.ibda.vto.ui.theme.fontFamilySemiBold

@Composable
fun LoginScreen(
//    homeViewModel: HomeVewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Image(
            painter = painterResource(id = R.drawable.home_asset),
            contentDescription = "Positioned Image",
            modifier = Modifier
                .absoluteOffset(x = 100.dp, y = (-100).dp)
                .size(1094.dp)
        )
        Column(
            modifier = Modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 60.dp, start = 20.dp, end = 20.dp, bottom = 60.dp)
            ) {
                Text(
                    "Welcome",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = stringResource(R.string.sign_up_welcome),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
            LoginBottomSheet(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(800.dp)
                    .shadow(
                        elevation = 5.dp
                    )
                    .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
            )
        }
    }
}

@Composable
fun LoginBottomSheet(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(top = 40.dp)
            )
            LoginForm(
                {},
                modifier = Modifier
                    .padding(vertical = 40.dp, horizontal = 20.dp)
            )
            Row(
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(R.string.sign_up_question),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = " Login",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Composable
fun TextInputForm(
    label: String,
    supportingText: String = "",
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf("") }

    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        singleLine = true,
        maxLines = 1,
        label = { Text(label) },
        supportingText = { Text(supportingText) },
        modifier = modifier
    )
}

@Composable
fun LoginForm(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TextInputForm(
            "Username",
            modifier = Modifier
                .fillMaxWidth()
        )
        TextInputForm(
            "Password",
            stringResource(R.string.password_criteria),
            modifier = Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
        ) {
            Text(
                "Sign up",
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
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen()
    }
}