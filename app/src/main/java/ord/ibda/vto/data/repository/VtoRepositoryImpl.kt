package ord.ibda.vto.data.repository

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ord.ibda.vto.data.api.ApiService
import ord.ibda.vto.domain.repository.VtoRepository
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class VtoRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val appContext: Application
): VtoRepository {

    override suspend fun doNetworkCall() {
        TODO("Not yet implemented")
    }

    override suspend fun runTryOn(userImageUri: Uri, clothImageUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                // Save user image to temp file
                val inputStream = appContext.contentResolver.openInputStream(userImageUri)
                val tempFile = File.createTempFile("user_image", ".jpg", appContext.cacheDir)
                inputStream?.use { input ->
                    FileOutputStream(tempFile).use { output ->
                        input.copyTo(output)
                    }
                }

                // Prepare multipart
                val imagePart = MultipartBody.Part.createFormData(
                    "user_image",
                    tempFile.name,
                    tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                )
                val clothUrlPart = clothImageUrl.toRequestBody("text/plain".toMediaTypeOrNull())

                // API call
                val response = api.tryOn(imagePart, clothUrlPart)

                if (response.isSuccessful) {
                    Log.d("VTO", "✅ API returned ${response.body()?.contentLength()} bytes")
                    response.body()?.byteStream()?.use { input ->
                        val bitmap = BitmapFactory.decodeStream(input)
                        if (bitmap != null) {
                            Log.d("VTO", "✅ Bitmap decoded, size: ${bitmap.width}x${bitmap.height}")
                        } else {
                            Log.e("VTO", "❌ Failed to decode bitmap from response")
                        }
                        bitmap
                    }
                } else {
                    Log.e("VTO", "❌ API failed: ${response.code()} ${response.message()}")
                    null
                }

            } catch (e: Exception) {
                Log.e("VTO", "❌ Exception in runTryOn", e)
                null
            }
        }
    }
}