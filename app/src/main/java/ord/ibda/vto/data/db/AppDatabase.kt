package ord.ibda.vto.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ord.ibda.vto.data.models.rooms.UserTable


@Database(
    entities = [
        UserTable::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}