package ru.andvl.bugtracker.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.andvl.bugtracker.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE userId = :id")
    fun getUser(id: Int): Flow<User>

    @Query("DELETE FROM User")
    fun clearUsers()
}
