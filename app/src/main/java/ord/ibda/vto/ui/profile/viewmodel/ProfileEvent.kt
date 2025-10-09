package ord.ibda.vto.ui.profile.viewmodel

sealed class ProfileEvent {
    object LoadUser: ProfileEvent()
    object Logout: ProfileEvent()
}