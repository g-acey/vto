package ord.ibda.vto.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ord.ibda.vto.data.models.rooms.UserTable

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(userTable: UserTable)

    @Query("SELECT * FROM UserTable WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUserByUsernameAndPassword(username: String, password: String): UserTable?

}