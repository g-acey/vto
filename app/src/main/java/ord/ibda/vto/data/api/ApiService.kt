package ord.ibda.vto.data.api

import retrofit2.http.GET

interface ApiService {

    @GET("test")
    suspend fun doNetworkCall()
}