package org.example.project.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.project.database.room_database.UserDao
import org.example.project.database.room_database.UserDatabaseClass
import platform.Foundation.NSHomeDirectory


fun getDao(): UserDao {
    val dbFile = NSHomeDirectory() + "/users.db"
    return Room.databaseBuilder<UserDatabaseClass>(
        name = dbFile
    )
        .setDriver(BundledSQLiteDriver())
        .build()
        .userDao
}