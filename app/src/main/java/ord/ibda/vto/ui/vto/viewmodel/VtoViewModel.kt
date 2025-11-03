package ord.ibda.vto.ui.vto.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ord.ibda.vto.data.db.ProductDao
import ord.ibda.vto.domain.repository.VtoRepository
import javax.inject.Inject

@HiltViewModel
class VtoViewModel @Inject constructor(
    private val repository: VtoRepository,
    private val productDao: ProductDao
): ViewModel() {

    private val _state = MutableStateFlow(VtoState())
    val state: StateFlow<VtoState> = _state

    fun onEvent(event: VtoEvent) {
        when (event) {
            VtoEvent.RemoveUserImage -> {
                _state.value = _state.value.copy(
                    userImageUri = null,
                    resultBitmap = null,
                    errorMessage = null
                )
            }

            is VtoEvent.SetProductImage -> {
                _state.value = _state.value.copy(productImageUrl = event.url)
            }

            is VtoEvent.SetUserImage -> {
                _state.value = _state.value.copy(userImageUri = event.uri)
            }

            is VtoEvent.LoadProductById -> {
                loadProductById(event.productId)
            }

            VtoEvent.TryOn -> {
                tryOn()
            }
        }
    }

    private fun loadProductById(productId: Int) {
        viewModelScope.launch {
            try {
                val product = productDao.getProductById(productId)
                if (product != null) {
                    _state.value = _state.value.copy(productImageUrl = product.product_image)
                } else {
                    _state.value = _state.value.copy(
                        errorMessage = "Product not found"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = _state.value.copy(
                    errorMessage = "Failed to load product"
                )
            }
        }
    }

    private fun tryOn() {
        val userImageUri = _state.value.userImageUri ?: return
        val productImageUrl = _state.value.productImageUrl ?: return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            try {
                val bitmap: Bitmap? = repository.runTryOn(Uri.parse(userImageUri), productImageUrl)

                if (bitmap != null) {
                    _state.value = _state.value.copy(
                        resultBitmap = bitmap,
                        isLoading = false,
                        userImageUri = null
                    )
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = "Failed to generate try-on result"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Unexpected error"
                )
            }
        }
    }
}