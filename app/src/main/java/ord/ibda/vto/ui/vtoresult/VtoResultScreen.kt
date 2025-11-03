package ord.ibda.vto.ui.vtoresult

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import ord.ibda.vto.ui.vtoresult.viewmodel.VtoResultEvent
import ord.ibda.vto.ui.vtoresult.viewmodel.VtoResultViewModel

@Composable
fun VtoResultScreen(
    modifier: Modifier = Modifier,
    imageUri: String?,
    onBack: () -> Unit,
    onSave: (Uri) -> Unit,
    vtoResultViewModel: VtoResultViewModel,
) {
//    val vtoResultState by vtoResultViewModel.state.collectAsState()
    val context = LocalContext.current
    val bitmap = rememberAsyncImagePainter(imageUri)

    Scaffold(
        topBar = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onBack() }
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "Try-On Result",
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (imageUri != null) {
                Image(
                    painter = bitmap,
                    contentDescription = "Result",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(475.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    onClick = {
                    val savedUri = saveImageToGallery(context, imageUri)
                    savedUri?.let { savedUri ->
                        vtoResultViewModel.onEvent(VtoResultEvent.SaveImage)
                        onSave(savedUri)
                    }
                }) {
                    Text(
                        text = "Save Image",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                Text("No result yet.")
            }
        }
    }
}

// Helper
fun saveImageToGallery(context: Context, imageUri: String): Uri? {
    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(imageUri))
    val savedImageURL = MediaStore.Images.Media.insertImage(
        context.contentResolver, bitmap, "VTO_${System.currentTimeMillis()}", "Try-on result"
    )
    return Uri.parse(savedImageURL)
}

//@Preview(showBackground = true)
//@Composable
//fun VtoResultScreenPreview() {
//    AppTheme {
//        VtoResultScreen("test", {}, {})
//    }
//}
