package ord.ibda.vto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import kotlinx.serialization.Serializable
import ord.ibda.vto.ui.home.HomeScreen
import ord.ibda.vto.ui.login.LoginScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import ord.ibda.vto.ui.cart.CartScreen
import ord.ibda.vto.ui.cart.viewmodel.CartViewModel
import ord.ibda.vto.ui.component.BottomNavigationBar
import ord.ibda.vto.ui.component.LoadingScreen
import ord.ibda.vto.ui.editprofile.EditProfileScreen
import ord.ibda.vto.ui.myorders.MyOrdersScreen
import ord.ibda.vto.ui.productdetails.ProductDetailsScreen
import ord.ibda.vto.ui.profile.ProfileScreen
import ord.ibda.vto.ui.session.viewmodel.SessionViewModel
import ord.ibda.vto.ui.signup.SignUpScreen
import ord.ibda.vto.ui.welcome.WelcomeScreen

@Serializable
data object WelcomeScreenNK: NavKey

@Serializable
data object SignUpScreenNK: NavKey

@Serializable
data object LoginScreenNK: NavKey

@Serializable
data object HomeScreenNK: NavKey

@Serializable
data class ProductDetailScreenNK(val productId: Int): NavKey

@Serializable
data object CartScreenNK: NavKey

@Serializable
data object ProfileScreenNK: NavKey

@Serializable
data object EditProfileScreenNK: NavKey

@Serializable
data object MyOrdersScreenNK: NavKey

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val loggedInUserId by sessionViewModel.loggedInUserId.collectAsState()
    val isInitialized by sessionViewModel.isInitialized.collectAsState()
    val cartState by cartViewModel.state.collectAsState()

    if (!isInitialized) {
        LoadingScreen()
        return
    }

    val startDestination = if (loggedInUserId != null) {
        HomeScreenNK
    } else {
        WelcomeScreenNK
    }

    val backStack = rememberNavBackStack(startDestination)

    LaunchedEffect(loggedInUserId) {
        if (loggedInUserId == null) {
            backStack.clear()
            backStack.add(WelcomeScreenNK)
        } else {
            backStack.clear()
            backStack.add(HomeScreenNK)
        }
    }

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
                        SignUpScreen(
                            onSignUpSuccess = { userId ->
                                sessionViewModel.login(userId)
                                backStack.clear()
                                backStack.add(HomeScreenNK)
                            },
                            goLogin = { backStack.removeLastOrNull() }
                        )
                    }
                }
                is LoginScreenNK -> {
                    NavEntry(
                        key = key
                    ) {
                        LoginScreen(
                            onLoginSuccess = { userId ->
                                sessionViewModel.login(userId)
                                backStack.clear()
                                backStack.add(HomeScreenNK)
                            },
                            goSignUp = { backStack.add(SignUpScreenNK) }
                        )
                    }
                }
                is HomeScreenNK -> {
                    NavEntry(
                        key = key
                    ) {
                        HomeScreen(
                            goProfile = { backStack.add(ProfileScreenNK)},
                            goProductDetails = { productId ->
                                backStack.add(ProductDetailScreenNK(productId))
                            },
                            bottomBar = {
                                BottomNavigationBar(
                                    currentDestination = HomeScreenNK,
                                    onNavigate = { dest -> backStack.add(dest) },
                                    cartItemCount = cartState.itemCount
                                )
                            }
                        )
                    }
                }
                is ProductDetailScreenNK -> {
                    NavEntry(
                        key = key
                    ) {
                        ProductDetailsScreen(
                            productId = key.productId,
                            onBack = { backStack.removeLastOrNull() },
                            onGoToCart = { backStack.add(CartScreenNK) }
                        )
                    }
                }
                is CartScreenNK -> {
                    NavEntry(
                        key = key
                    ) {
                        CartScreen(
                            bottomBar = {
                                BottomNavigationBar(
                                    currentDestination = CartScreenNK,
                                    onNavigate = { dest -> backStack.add(dest) },
                                    cartItemCount = cartState.itemCount
                                )
                            }
                        )
                    }
                }
                is ProfileScreenNK -> {
                    NavEntry(
                        key = key
                    ) {
                        ProfileScreen(
                            onLogout = {
                                sessionViewModel.logout()
                            },
                            goOrders = {
                                backStack.add(MyOrdersScreenNK)
                            },
                            goEditProfile = {
                                backStack.add(EditProfileScreenNK)
                            }
                        )
                    }
                }
                is EditProfileScreenNK -> {
                    NavEntry(
                        key = key
                    ) {
                        EditProfileScreen()
                    }
                }
                is MyOrdersScreenNK -> {
                    NavEntry(
                        key = key
                    ) {
                        MyOrdersScreen()
                    }
                }
                else -> throw RuntimeException("Invalid Navkey.")
            }
        }
    )
}



