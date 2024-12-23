package org.example.project.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.project.database.room_database.UserDao
import org.example.project.database.room_database.UserDatabaseClass
import java.io.File


fun getDao(): UserDao {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "users.db")
    return Room.databaseBuilder<UserDatabaseClass>(
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
        .userDao
}