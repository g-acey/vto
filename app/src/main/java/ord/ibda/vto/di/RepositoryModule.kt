package ord.ibda.vto.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ord.ibda.vto.data.repository.VtoRepositoryImpl
import ord.ibda.vto.domain.repository.VtoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindVtoRepository(
        vtoRepositoryImpl: VtoRepositoryImpl
    ): VtoRepository
}