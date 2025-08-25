package ord.ibda.vto.ui.login.viewmodel

sealed class LoginEvent {
    data class UserLogin(val onSuccess: (Int) -> Unit): LoginEvent()
    data class InputUsername(val username: String): LoginEvent()
    data class InputPassword(val password: String): LoginEvent()
    object ChangePasswordVisibility: LoginEvent()
    object ClearValue: LoginEvent()
}