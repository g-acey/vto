package ord.ibda.vto.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ord.ibda.vto.data.models.rooms.CartTable
import ord.ibda.vto.data.models.rooms.OrderItemTable
import ord.ibda.vto.data.models.rooms.OrderTable
import ord.ibda.vto.data.models.rooms.ProductTable
import ord.ibda.vto.data.models.rooms.UserTable


@Database(
    entities = [
        UserTable::class,
        ProductTable::class,
        CartTable::class,
        OrderTable::class,
        OrderItemTable::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun productDao(): ProductDao

    abstract fun cartDao(): CartDao

    abstract fun orderDao(): OrderDao
}