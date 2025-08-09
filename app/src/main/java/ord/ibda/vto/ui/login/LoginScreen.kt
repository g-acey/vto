package ord.ibda.vto.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ord.ibda.vto.R
import ord.ibda.vto.ui.login.viewmodel.LoginEvent
import ord.ibda.vto.ui.login.viewmodel.LoginViewModel
import ord.ibda.vto.ui.signup.TextInputForm
import ord.ibda.vto.ui.theme.AppTheme


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val loginState by loginViewModel.state.collectAsState()

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
                    text = "Welcome",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = stringResource(R.string.login_welcome),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
            LoginBottomSheet(
                usernameValue = loginState.username,
                passwordValue = loginState.password,
                usernameChange = { loginViewModel.onEvent(LoginEvent.InputUsername(it)) },
                passwordChange = { loginViewModel.onEvent(LoginEvent.InputPassword(it)) },
                valueClear = { loginViewModel.onEvent(LoginEvent.ClearValue) },
                isVisible = loginState.isPasswordVisible,
                changeVisibility = { loginViewModel.onEvent(LoginEvent.ChangePasswordVisibility) },
                submitForm= { loginViewModel.onEvent(LoginEvent.UserLogin) },
                clickText= {  },
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
    usernameValue: String,
    passwordValue: String,
    usernameChange: (String) -> Unit,
    passwordChange: (String) -> Unit,
    valueClear: () -> Unit,
    isVisible: Boolean,
    changeVisibility: () -> Unit,
    submitForm: () -> Unit,
    clickText: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .padding(bottom = 60.dp)
        ) {
            Text(
                text = "Let's sign you in",
                style = MaterialTheme.typography.headlineMedium,
            )
            LoginForm(
                usernameValue = usernameValue,
                passwordValue = passwordValue,
                usernameChange = usernameChange,
                passwordChange = passwordChange,
                valueClear = valueClear,
                isVisible = isVisible,
                changeVisibility = changeVisibility,
                submitForm = submitForm,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
            Row(
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(R.string.login_question),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = " Sign up",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable { clickText() }
                )
            }
        }
    }
}

@Composable
fun PasswordInputForm(
    value: String,
    valueChange: (String) -> Unit,
    isVisible: Boolean,
    changeVisibility: () -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { valueChange },
        label = { Text(text = label) },
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (value.isNotEmpty()) {
                val image = if (isVisible)
                    Icons.Default.Visibility
                else Icons.Default.VisibilityOff

                IconButton(onClick = { changeVisibility }) {
                    Icon(imageVector = image, contentDescription = if (isVisible) "Hide password" else "Show password")
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun LoginForm(
    usernameValue: String,
    passwordValue: String,
    usernameChange: (String) -> Unit,
    passwordChange: (String) -> Unit,
    valueClear: () -> Unit,
    isVisible: Boolean,
    changeVisibility: () -> Unit,
    submitForm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        TextInputForm(
            value = usernameValue,
            valueChange = usernameChange,
            valueClear = valueClear,
            label = "Username",
            modifier = Modifier
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier,
        ) {
            PasswordInputForm(
                value = passwordValue,
                valueChange = passwordChange,
                isVisible = isVisible,
                changeVisibility = changeVisibility,
                label = "Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
            Text(
                text = stringResource(R.string.login_forget),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Button(
            onClick = { submitForm() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
        ) {
            Text(
                text = "Sign up",
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