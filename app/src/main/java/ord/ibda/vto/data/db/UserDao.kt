package ord.ibda.vto.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import ord.ibda.vto.data.models.rooms.UserTable

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(userTable: UserTable): Long

    @Query("SELECT * FROM UserTable WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUserByUsernameAndPassword(username: String, password: String): UserTable?

    @Query("SELECT * FROM UserTable WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserTable?

    @Query("SELECT * FROM UserTable WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserTable?

    @Query("UPDATE UserTable SET username = :username, password = :password WHERE id = :userId")
    suspend fun updateUserInfo(userId: Int, username: String, password: String)
}