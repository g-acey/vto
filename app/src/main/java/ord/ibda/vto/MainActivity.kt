package ord.ibda.vto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import ord.ibda.vto.ui.navigation.NavigationRoot
import ord.ibda.vto.ui.theme.AppTheme
import ord.ibda.vto.ui.vto.VtoScreen
import ord.ibda.vto.ui.vto.viewmodel.VtoViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    NavigationRoot()
                }
            }
        }
    }
}
