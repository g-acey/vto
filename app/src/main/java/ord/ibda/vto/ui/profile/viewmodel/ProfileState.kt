package ord.ibda.vto.ui.profile.viewmodel

data class ProfileState(
    val username: String = "",
    val isLoading: Boolean = false,
    val isLoggingOut: Boolean = false
)