package ord.ibda.vto.ui.login.viewmodel

sealed class LoginEvent {
    object UserLogin: LoginEvent()
    data class InputUsername(val username: String): LoginEvent()
    data class InputPassword(val password: String): LoginEvent()
    object ChangePasswordVisibility: LoginEvent()
    object ShowError: LoginEvent()
    object ClearValue: LoginEvent()
}