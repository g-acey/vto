package ord.ibda.vto.data.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.sessionDataStore by preferencesDataStore("session_prefs")

class SessionManager(private val context: Context) {

    companion object {
        private val USER_ID_KEY = intPreferencesKey("user_id")
    }

    val loggedInUserId: Flow<Int?> = context.sessionDataStore.data.map { prefs ->
        prefs[USER_ID_KEY]
    }

    suspend fun saveUserId(userId: Int) {
        context.sessionDataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
        }
    }

    suspend fun clearUserId() {
        context.sessionDataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
        }
    }
}