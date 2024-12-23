package org.example.project.database.room_database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "users"
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int
)
