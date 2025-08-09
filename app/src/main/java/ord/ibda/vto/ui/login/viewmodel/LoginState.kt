package ord.ibda.vto.ui.login.viewmodel

data class LoginState (
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isError: Boolean = false,
    val isLoggedIn: Boolean = false
)