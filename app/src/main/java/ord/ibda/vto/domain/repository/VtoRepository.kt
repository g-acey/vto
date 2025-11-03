package ord.ibda.vto.domain.repository

import android.graphics.Bitmap
import android.net.Uri

interface VtoRepository {
    suspend fun doNetworkCall()
    suspend fun runTryOn(userImageUri: Uri, clothImageUrl: String): Bitmap?
}