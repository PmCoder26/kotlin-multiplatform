package org.example.project.database.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: User)

    @Delete
    suspend fun removeUser(user: User)

    @Query(value = "select * from users")
    fun getAllUsers(): Flow<List<User>>

}