package ord.ibda.vto.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ord.ibda.vto.data.models.rooms.ProductTable
import ord.ibda.vto.data.models.rooms.UserTable


@Database(
    entities = [
        UserTable::class,
        ProductTable::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun productDao(): ProductDao
}