package org.example.project.database

import android.content.Context
import androidx.room.Room
import org.example.project.database.room_database.UserDao
import org.example.project.database.room_database.UserDatabaseClass


fun getDao(context: Context): UserDao {
    val dbFile = context.getDatabasePath("users.db")
    return Room.databaseBuilder<UserDatabaseClass>(
        context.applicationContext,
        UserDatabaseClass::class.java,
        dbFile.absolutePath
    ).build()
        .userDao
}