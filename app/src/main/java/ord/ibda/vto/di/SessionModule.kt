package ord.ibda.vto.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ord.ibda.vto.data.session.SessionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SessionModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }
}