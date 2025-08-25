package ord.ibda.vto.ui.signup.viewmodel

sealed class SignUpEvent {
    data class UserSignUp(val onSuccess: (Int) -> Unit): SignUpEvent()
    data class InputUsername(val username: String): SignUpEvent()
    data class InputPassword(val password: String): SignUpEvent()
    object ChangePasswordVisibility: SignUpEvent()
    object ClearValue: SignUpEvent()
}