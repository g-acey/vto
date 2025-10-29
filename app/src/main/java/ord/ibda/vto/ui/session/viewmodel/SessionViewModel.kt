package ord.ibda.vto.ui.session.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ord.ibda.vto.data.db.UserDao
import ord.ibda.vto.data.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val userDao: UserDao
) : ViewModel() {

    private val _loggedInUserId = MutableStateFlow<Int?>(null)
    val loggedInUserId: StateFlow<Int?> = _loggedInUserId

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized

    init {
        viewModelScope.launch {
            sessionManager.loggedInUserId.collect { id ->
                if (id != null) {
                    val userExists = userDao.getUserById(id) != null
                    if (userExists) {
                        _loggedInUserId.value = id
                    } else {
                        // User doesn't exist anymore -> clear session
                        sessionManager.clearUserId()
                        _loggedInUserId.value = null
                    }
                } else {
                    _loggedInUserId.value = null
                }
                _isInitialized.value = true
            }
        }
    }

    fun login(userId: Int) {
        viewModelScope.launch {
            sessionManager.saveUserId(userId)
            _loggedInUserId.value = userId
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearUserId()
            _loggedInUserId.value = null
        }
    }
}