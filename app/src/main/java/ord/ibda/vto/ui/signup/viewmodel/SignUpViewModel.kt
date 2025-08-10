package ord.ibda.vto.ui.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ord.ibda.vto.data.db.UserDao
import ord.ibda.vto.data.models.rooms.UserTable
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userDao: UserDao
): ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state

    fun onEvent(event: SignUpEvent) {
        when(event) {
            SignUpEvent.ChangePasswordVisibility -> {
                _state.update { it.copy(
                    isPasswordVisible = !_state.value.isPasswordVisible
                ) }
            }
            SignUpEvent.ClearValue -> {
                _state.update { it.copy(
                    username = ""
                ) }
            }
            is SignUpEvent.InputPassword -> {
                _state.update { it.copy(
                    password = event.password
                ) }
            }
            is SignUpEvent.InputUsername -> {
                _state.update { it.copy(
                    username = event.username
                ) }
            }
            SignUpEvent.UserSignUp -> {
                val username = _state.value.username.trim()
                val password = _state.value.password

                val isPasswordValid = password.length >= 6 &&
                        password.any { it.isDigit() } &&
                        password.any { it.isUpperCase() }

                viewModelScope.launch {
                    var isValid = true

                    if (!isPasswordValid) {
                        _state.update { it.copy(isErrorPassword = true) }
                        isValid = false
                    } else {
                        _state.update { it.copy(isErrorPassword = false) }
                    }

                    val existingUser = userDao.getUserByUsername(username)
                    if (existingUser != null) {
                        _state.update { it.copy(isErrorUsername = true) }
                        isValid = false
                    } else {
                        _state.update { it.copy(isErrorUsername = false) }
                    }

                    if (isValid) {
                        userDao.insertUser(UserTable(username = username, password = password))
                        _state.update { it.copy(
                            signUpSuccess = true,
                            username = "",
                            password = ""
                        ) }

                    }
                }
            }
        }
    }
}