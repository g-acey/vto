package ord.ibda.vto.ui.editprofile.viewmodel

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
class EditProfileViewModel @Inject constructor(
    private val userDao: UserDao,
    private val sessionManager: SessionManager
): ViewModel() {

    private val _state = MutableStateFlow(EditProfileState())
    val state: StateFlow<EditProfileState> = _state

    private var currentUserId: Int? = null

    init {
        viewModelScope.launch {
            sessionManager.loggedInUserId.collect { id ->
                currentUserId = id
                if (id != null) {
                    onEvent(EditProfileEvent.LoadUserProfile)
                }
            }
        }
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.UpdateUsername -> {
                _state.value = _state.value.copy(username = event.username)
            }

            is EditProfileEvent.UpdatePassword -> {
                _state.value = _state.value.copy(password = event.password)
            }

            EditProfileEvent.LoadUserProfile -> loadUserProfile()
            EditProfileEvent.SaveProfile -> saveProfile()
            EditProfileEvent.ClearMessage -> {
                _state.value = _state.value.copy(successMessage = null)
            }
        }
    }

    private fun loadUserProfile() {
        val userId = currentUserId ?: return
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val user = userDao.getUserById(userId)

            _state.value = _state.value.copy(
                username = user?.username ?: "",
                password = user?.password ?: "",
                originalUsername = user?.username ?: "",
                originalPassword = user?.password ?: ""
            )
        }
    }

    private fun saveProfile() {
        val userId = currentUserId ?: return
        val username = _state.value.username.trim()
        val password = _state.value.password

        viewModelScope.launch {
            val existingUser = userDao.getUserByUsername(username)
            val isUsernameTaken = existingUser != null && existingUser.id != userId

            if (isUsernameTaken) {
                _state.value = _state.value.copy(
                    isErrorUsername = true,
                    successMessage = null
                )
                return@launch
            }

            val isValidPassword = validatePassword(password)
            if (!isValidPassword) {
                _state.value = _state.value.copy(
                    isErrorPassword = true,
                    successMessage = null
                )
                return@launch
            }

            _state.value = _state.value.copy(isLoading = true)

            userDao.updateUserInfo(userId, username, password)

            _state.value = _state.value.copy(
                isLoading = false,
                isErrorUsername = false,
                isErrorPassword = false,
                successMessage = "Profile updated successfully!"
            )
        }
    }

    private fun validatePassword(password: String): Boolean {
        val hasMinLength = password.length >= 6
        val hasDigit = password.any { it.isDigit() }
        val hasUppercase = password.any { it.isUpperCase() }
        return hasMinLength && hasDigit && hasUppercase
    }

}