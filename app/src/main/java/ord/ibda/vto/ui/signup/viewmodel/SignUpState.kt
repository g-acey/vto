package ord.ibda.vto.ui.signup.viewmodel

data class SignUpState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isErrorPassword: Boolean = false,
    val isErrorUsername: Boolean = false,
//    val signUpSuccess: Boolean = false
)