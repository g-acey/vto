package ord.ibda.vto.domain.repository

interface VtoRepository {
    suspend fun doNetworkCall()
}