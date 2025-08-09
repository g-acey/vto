package ord.ibda.vto.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ord.ibda.vto.data.api.ApiService
import ord.ibda.vto.data.repository.VtoRepositoryImpl
import ord.ibda.vto.domain.repository.VtoRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://test.com")
            .build()
            .create(ApiService::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideVtoRepository(api: ApiService, app: Application): VtoRepository {
//        return VtoRepositoryImpl(api, app)
//    }
}