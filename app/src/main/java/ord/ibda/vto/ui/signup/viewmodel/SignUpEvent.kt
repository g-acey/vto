package ord.ibda.vto.ui.signup.viewmodel

import ord.ibda.vto.ui.login.viewmodel.LoginEvent

sealed class SignUpEvent {
    object UserSignUp: SignUpEvent()
    data class InputUsername(val username: String): SignUpEvent()
    data class InputPassword(val password: String): SignUpEvent()
    object ChangePasswordVisibility: SignUpEvent()
    object ClearValue: SignUpEvent()
}