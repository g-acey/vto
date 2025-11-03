package ord.ibda.vto.ui.vtoresult.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class VtoResultViewModel @Inject constructor(
): ViewModel() {

    private val _state = MutableStateFlow(VtoResultState())
    val state: StateFlow<VtoResultState> = _state

    fun onEvent(event: VtoResultEvent) {
        when(event) {
            VtoResultEvent.ClearSnackbar -> {
                _state.value = _state.value.copy(snackBarMessage = null)
            }

            VtoResultEvent.SaveImage -> {
                _state.value = _state.value.copy(snackBarMessage = "Image Saved!")
            }
        }
    }
}