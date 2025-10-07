package ord.ibda.vto.ui.session.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ord.ibda.vto.data.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _loggedInUserId = MutableStateFlow<Int?>(null)
    val loggedInUserId: StateFlow<Int?> = _loggedInUserId

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized

    init {
        viewModelScope.launch {
            sessionManager.loggedInUserId.collect { id ->
                _loggedInUserId.value = id
                _isInitialized.value = true
            }
        }
    }

    fun login(userId: Int) {
        viewModelScope.launch {
            sessionManager.saveUserId(userId)
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearUserId()
        }
    }

    fun isLoggedIn(): Boolean = _loggedInUserId.value != null
}