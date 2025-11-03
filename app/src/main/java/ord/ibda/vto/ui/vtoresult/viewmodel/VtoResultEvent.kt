package ord.ibda.vto.ui.vtoresult.viewmodel

sealed class VtoResultEvent {
    object ClearSnackbar : VtoResultEvent()
    object SaveImage: VtoResultEvent()
}