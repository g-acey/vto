package ord.ibda.vto.ui.signup

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
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ord.ibda.vto.R
import ord.ibda.vto.ui.signup.viewmodel.SignUpEvent
import ord.ibda.vto.ui.signup.viewmodel.SignUpViewModel
import ord.ibda.vto.ui.theme.AppTheme


@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    goLogin: () -> Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val signUpState by signUpViewModel.state.collectAsState()

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
                    text = stringResource(R.string.sign_up_welcome),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
            SignUpBottomSheet(
                usernameValue = signUpState.username,
                passwordValue = signUpState.password,
                usernameChange = { signUpViewModel.onEvent(SignUpEvent.InputUsername(it)) },
                passwordChange = { signUpViewModel.onEvent(SignUpEvent.InputPassword(it)) },
                valueClear = { signUpViewModel.onEvent(SignUpEvent.ClearValue) },
                isVisible = signUpState.isPasswordVisible,
                changeVisibility = { signUpViewModel.onEvent(SignUpEvent.ChangePasswordVisibility) },
                isErrorUsername = signUpState.isErrorUsername,
                isErrorPassword = signUpState.isErrorPassword,
                submitForm= { signUpViewModel.onEvent(SignUpEvent.UserSignUp) },
                clickText= { goLogin() },
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
fun SignUpBottomSheet(
    usernameValue: String,
    passwordValue: String,
    usernameChange: (String) -> Unit,
    passwordChange: (String) -> Unit,
    valueClear: () -> Unit,
    isVisible: Boolean,
    changeVisibility: () -> Unit,
    isErrorPassword: Boolean,
    isErrorUsername: Boolean,
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
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium,
            )
            SignUpForm(
                usernameValue = usernameValue,
                passwordValue = passwordValue,
                usernameChange = usernameChange,
                passwordChange = passwordChange,
                valueClear = valueClear,
                isVisible = isVisible,
                changeVisibility = changeVisibility,
                isErrorPassword = isErrorPassword,
                isErrorUsername = isErrorUsername,
                submitForm = submitForm,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
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
                    modifier = Modifier
                        .clickable { clickText() }
                )
            }
        }
    }
}

@Composable
fun TextInputForm(
    value: String,
    valueChange: (String) -> Unit,
    valueClear: () -> Unit,
    isError: Boolean,
    supportingText: @Composable () -> Unit = {},
    label: String,
    modifier: Modifier = Modifier
) {
      OutlinedTextField(
          value = value,
          onValueChange = valueChange,
          singleLine = true,
          isError = isError,
          supportingText = supportingText,
          maxLines = 1,
          label = { Text(label) },
          trailingIcon = {
              if (value.isNotEmpty()) {
                  IconButton(onClick = { valueClear() }) {
                      Icon(
                          imageVector = Icons.Default.Clear,
                          contentDescription = "Clear text"
                      )
                  }
              }
                         },
          modifier = modifier
      )
}

@Composable
fun PasswordInputForm(
    value: String,
    valueChange: (String) -> Unit,
    isVisible: Boolean,
    changeVisibility: () -> Unit,
    isError: Boolean,
    label: String,
    supportingText: String = "",
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = valueChange,
        label = { Text(text = label) },
        isError = isError,
        supportingText = { Text(text = supportingText) },
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (value.isNotEmpty()) {
                val image = if (isVisible)
                    Icons.Default.Visibility
                else Icons.Default.VisibilityOff

                IconButton(onClick = { changeVisibility() }) {
                    Icon(imageVector = image, contentDescription = if (isVisible) "Hide password" else "Show password")
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun SignUpForm(
    usernameValue: String,
    passwordValue: String,
    usernameChange: (String) -> Unit,
    passwordChange: (String) -> Unit,
    valueClear: () -> Unit,
    isErrorPassword: Boolean,
    isErrorUsername: Boolean,
    isVisible: Boolean,
    changeVisibility: () -> Unit,
    submitForm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TextInputForm(
            value = usernameValue,
            valueChange = usernameChange,
            valueClear = valueClear,
            label = "Username",
            isError = isErrorUsername,
            supportingText = { if (isErrorUsername) { Text(text = "* Username already taken") } },
            modifier = Modifier
                .fillMaxWidth()
        )
        PasswordInputForm(
            value = passwordValue,
            valueChange = passwordChange,
            isVisible = isVisible,
            changeVisibility = changeVisibility,
            isError = isErrorPassword,
            label = "Password",
            supportingText =  stringResource(R.string.password_criteria),
            modifier = Modifier
                .fillMaxWidth()
        )
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
fun SignUpScreenPreview() {
    AppTheme {
        SignUpScreen(goLogin = {})
    }
}