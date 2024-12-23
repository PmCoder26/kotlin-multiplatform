package org.example.project.database.room_database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor


@Database(entities = [User::class], version = 1)
@ConstructedBy(UserDatabaseConstructor::class)
abstract class UserDatabaseClass : RoomDatabase() {

    abstract val userDao: UserDao

}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object UserDatabaseConstructor : RoomDatabaseConstructor<UserDatabaseClass> {
    override fun initialize(): UserDatabaseClass
}
