package ord.ibda.vto.ui.vto.viewmodel

data class VtoState(
    val userImageUri: String? = null,
    val productImageUrl: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)