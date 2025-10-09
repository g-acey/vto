package ord.ibda.vto.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ord.ibda.vto.data.db.UserDao
import ord.ibda.vto.data.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val userDao: UserDao
): ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    fun onEvent(event: ProfileEvent) {
        when(event) {
            ProfileEvent.LoadUser -> loadUser()
            ProfileEvent.Logout -> logout()
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val userId = sessionManager.loggedInUserId.first()
            val user = userId?.let { userDao.getUserById(it) }

            _state.value = _state.value.copy(
                username = user?.username ?: "Guest",
                isLoading = false
            )
        }
    }

    private fun logout() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoggingOut = true)
            sessionManager.clearUserId()
            _state.value = _state.value.copy(isLoggingOut = false)
        }
    }
}