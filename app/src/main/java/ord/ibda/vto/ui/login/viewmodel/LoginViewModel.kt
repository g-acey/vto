package ord.ibda.vto.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ord.ibda.vto.data.db.UserDao
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDao: UserDao
): ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.InputPassword -> {
                _state.update { it.copy(
                    password = event.password
                ) }
            }
            is LoginEvent.InputUsername -> {
                _state.update { it.copy(
                    username = event.username
                ) }
            }
            LoginEvent.ChangePasswordVisibility -> {
                _state.update { it.copy(
                    isPasswordVisible = !_state.value.isPasswordVisible
                ) }
            }
            is LoginEvent.UserLogin -> {
                val username = _state.value.username.trim()
                val password = _state.value.password

                viewModelScope.launch {
                    val user = userDao.getUserByUsernameAndPassword(username, password)
                    if (user != null) {
                        _state.update { it.copy(
                            isError = false,
//                            isLoggedIn = true,
                            username = "",
                            password = "",
                        ) }
                        event.onSuccess(user.id)
                    } else {
                        _state.update { it.copy(
                            isError = true,
//                            isLoggedIn = false
                        ) }
                    }
                }
            }
            LoginEvent.ClearValue -> {
                _state.update { it.copy(
                    username = ""
                ) }
            }
        }
    }
}