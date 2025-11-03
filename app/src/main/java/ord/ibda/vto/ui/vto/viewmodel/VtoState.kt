package ord.ibda.vto.ui.vto.viewmodel

import android.graphics.Bitmap

data class VtoState(
    val userImageUri: String? = null,
    val productImageUrl: String? = null,
    val resultBitmap: Bitmap? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)