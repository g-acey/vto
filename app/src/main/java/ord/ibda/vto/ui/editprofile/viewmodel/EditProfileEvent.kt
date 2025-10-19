package ord.ibda.vto.ui.editprofile.viewmodel

sealed class EditProfileEvent {
    data class UpdateUsername(val username: String) : EditProfileEvent()
    data class UpdatePassword(val password: String) : EditProfileEvent()
    object LoadUserProfile : EditProfileEvent()
    object SaveProfile : EditProfileEvent()
    object ClearMessage : EditProfileEvent()
}