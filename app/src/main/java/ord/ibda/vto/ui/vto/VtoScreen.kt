package ord.ibda.vto.ui.vto

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import ord.ibda.vto.ui.vto.viewmodel.VtoEvent
import ord.ibda.vto.ui.vto.viewmodel.VtoViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VtoScreen(
    modifier: Modifier = Modifier,
    productId: Int,
    vtoViewModel: VtoViewModel,
    onBack: () -> Unit,
) {
    val vtoState by vtoViewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(productId) {
        vtoViewModel.onEvent(VtoEvent.LoadProductById(productId))
    }

    // Modal Bottom Sheet
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }

    // Launchers
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { vtoViewModel.onEvent(VtoEvent.SetUserImage(it.toString())) }
            showSheet = false
        }
    )

    fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
        val file = File(context.cacheDir, "captured_${System.currentTimeMillis()}.jpg")
        file.outputStream().use { out -> bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out) }
        return file.toUri()
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            bitmap?.let {
                val uri = saveBitmapToCache(context, it)
                vtoViewModel.onEvent(VtoEvent.SetUserImage(uri.toString()))
            }
            showSheet = false
        }
    )

    // Bottom Sheet for choosing image source
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            dragHandle = {  },
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select File",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                cameraLauncher.launch(null)
                                showSheet = false
                            }
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PhotoCamera,
                            contentDescription = "Camera",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Camera",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                imagePicker.launch("image/*")
                                showSheet = false
                            }
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Image,
                            contentDescription = "Gallery",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Gallery",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            bottomBar = {
                Surface(
                    tonalElevation = 6.dp,
                    shadowElevation = 8.dp,
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(105.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Button(
                            onClick = { vtoViewModel.onEvent(VtoEvent.TryOn) },
                            enabled = vtoState.userImageUri != null && !vtoState.isLoading,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(53.dp)
                        ) {
                            Text(
                                text = if (vtoState.isLoading) "Loading..." else "Try out",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.labelLarge,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            },
            modifier = modifier
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(contentPadding)
            ) {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    VtoHeader(onBack = onBack)
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = "Upload a photo of yourself",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "For best results, use a clear background and good lighting.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(475.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { showSheet = true },
                        contentAlignment = Alignment.Center
                    ) {
                        if (vtoState.userImageUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(vtoState.userImageUri),
                                contentDescription = "User Image Preview",
                                modifier = Modifier.fillMaxSize()
                            )

                            IconButton(
                                onClick = { vtoViewModel.onEvent(VtoEvent.RemoveUserImage) },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(vertical = 20.dp, horizontal = 20.dp)
                                    .size(28.dp)
                                    .background(
                                        MaterialTheme.colorScheme.surfaceContainerLow,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Remove Image",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                contentDescription = "Upload Placeholder",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        if (vtoState.isLoading) {
            LoadingOverlay()
        }
    }
}

@Composable
fun VtoHeader(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
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
            text = "Virtual Try-on",
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Composable
fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun VtoScreenPreview() {
//    AppTheme {
//        VtoScreen()
//    }
//}