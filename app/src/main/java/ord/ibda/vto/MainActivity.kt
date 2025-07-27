package ord.ibda.vto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ord.ibda.vto.ui.cartscreen.CartScreen
import ord.ibda.vto.ui.checkoutscreen.CheckoutScreen
import ord.ibda.vto.ui.editprofilescreen.EditProfileScreen
import ord.ibda.vto.ui.loginscreen.LoginForm
import ord.ibda.vto.ui.loginscreen.LoginScreen
import ord.ibda.vto.ui.productdetailsscreen.ProductDetailsScreen
import ord.ibda.vto.ui.profilescreen.ProfileScreen
import ord.ibda.vto.ui.signupscreen.SignUpScreen
import ord.ibda.vto.ui.theme.AppTheme
import ord.ibda.vto.ui.welcomescreen.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EditProfileScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
