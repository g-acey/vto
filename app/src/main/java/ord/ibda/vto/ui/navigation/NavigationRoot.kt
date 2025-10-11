package ord.ibda.vto.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import ord.ibda.vto.ui.cart.CartScreen
import ord.ibda.vto.ui.cart.viewmodel.CartViewModel
import ord.ibda.vto.ui.checkout.CheckoutScreen
import ord.ibda.vto.ui.checkout.viewmodel.CheckoutEvent
import ord.ibda.vto.ui.checkout.viewmodel.CheckoutViewModel
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
data class CheckoutScreenNK(val orderId: Int): NavKey

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
    cartViewModel: CartViewModel = hiltViewModel(),
    checkoutViewModel: CheckoutViewModel = hiltViewModel()
) {
    val loggedInUserId by sessionViewModel.loggedInUserId.collectAsState()
    val isInitialized by sessionViewModel.isInitialized.collectAsState()
    val cartState by cartViewModel.state.collectAsState()
    val checkoutState by checkoutViewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

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

    LaunchedEffect(checkoutState.snackbarMessage) {
        checkoutState.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            checkoutViewModel.onEvent(CheckoutEvent.ClearSnackbar)
        }
    }

    LaunchedEffect(loggedInUserId) {
        if (loggedInUserId == null) {
            backStack.clear()
            backStack.add(WelcomeScreenNK)
        } else {
            backStack.clear()
            backStack.add(HomeScreenNK)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        NavDisplay(
            modifier = modifier,
            backStack = backStack,
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
                rememberSceneSetupNavEntryDecorator()
            ),
            entryProvider = { key ->
                when (key) {
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
                                goProfile = { backStack.add(ProfileScreenNK) },
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
                                },
                                goCheckoutScreen = { orderId ->
                                    backStack.add(CheckoutScreenNK(orderId))
                                },
                                cartViewModel = cartViewModel
                            )
                        }
                    }

                    is CheckoutScreenNK -> {
                        NavEntry(
                            key = key
                        ) {
                            CheckoutScreen(
                                orderId = key.orderId,
                                gobacktoCart = {
                                    backStack.removeLastOrNull()
                                },
                                goHome = {
                                    backStack.clear()
                                    backStack.add(HomeScreenNK)
                                },
                                checkoutViewModel = checkoutViewModel
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
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .shadow(8.dp, RoundedCornerShape(8.dp))
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
//                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = data.visuals.message,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}



