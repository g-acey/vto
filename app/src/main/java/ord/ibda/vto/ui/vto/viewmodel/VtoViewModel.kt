package ord.ibda.vto.ui.vto.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ord.ibda.vto.domain.repository.VtoRepository
import javax.inject.Inject

@HiltViewModel
class VtoViewModel @Inject constructor(
    private val repository: VtoRepository
): ViewModel() {

    private val _state = MutableStateFlow(VtoState())
    val state: StateFlow<VtoState> = _state

    fun onEvent(event: VtoEvent) {
        when(event) {
            VtoEvent.RemoveUserImage -> {
                _state.value = _state.value.copy(userImageUri = null)
            }
            is VtoEvent.SetProductImage -> {
                _state.value = _state.value.copy(productImageUrl = event.url)
            }
            is VtoEvent.SetUserImage -> {
                _state.value = _state.value.copy(userImageUri = event.uri)
            }
        }
    }
}