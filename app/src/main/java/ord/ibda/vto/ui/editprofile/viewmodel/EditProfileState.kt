package ord.ibda.vto.ui.editprofile.viewmodel

data class EditProfileState(
    val username: String = "",
    val password: String = "",
    val isErrorUsername: Boolean = false,
    val isErrorPassword: Boolean = false,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val originalUsername: String = "",
    val originalPassword: String = "",
) {
    val canSave: Boolean
        get() = username.isNotBlank() &&
                password.isNotBlank() &&
                (username.trim() != originalUsername || password != originalPassword)
}