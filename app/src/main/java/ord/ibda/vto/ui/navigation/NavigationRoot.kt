package ord.ibda.vto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import kotlinx.serialization.Serializable
import ord.ibda.vto.ui.login.LoginScreen
import ord.ibda.vto.ui.signup.SignUpScreen
import ord.ibda.vto.ui.welcome.WelcomeScreen

@Serializable
data object WelcomeScreenNK: NavKey

@Serializable
data object SignUpScreenNK: NavKey

@Serializable
data object LoginScreenNK: NavKey

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(WelcomeScreenNK)
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = { key ->
            when(key) {
                is WelcomeScreenNK -> {
                    NavEntry(
                        key = key
                    ) {
                        WelcomeScreen(
                            goLogin = {
                                backStack.add(LoginScreenNK)
                            }
                        )
                    }
                }
                is SignUpScreenNK -> {
                    NavEntry(
                        key = key
                    ) {
                        SignUpScreen()
                    }
                }
                is LoginScreenNK -> {
                    NavEntry(
                        key = key
                    ) {
                        LoginScreen()
                    }
                }
                else -> throw RuntimeException("Invalid Navkey.")
            }
        }
    )
}