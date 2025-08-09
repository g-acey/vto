package ord.ibda.vto.data.repository

import android.app.Application
import ord.ibda.vto.R
import ord.ibda.vto.data.api.ApiService
import ord.ibda.vto.domain.repository.VtoRepository
import javax.inject.Inject

class VtoRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val appContext: Application
): VtoRepository {

    init {
        val appName = appContext.getString(R.string.app_name)
        println("Hello from the repository. The app name os $appName")
    }

    override suspend fun doNetworkCall() {
        TODO("Not yet implemented")
    }
}