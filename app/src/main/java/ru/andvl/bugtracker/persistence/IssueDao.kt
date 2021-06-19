package ru.andvl.bugtracker.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.andvl.bugtracker.model.Issue

@Dao
interface IssueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIssue(issue: Issue)

    @Query("SELECT * FROM Issue")
    fun getIssues(): Flow<List<Issue>>

    @Query("SELECT * FROM Issue WHERE id = :id")
    fun getIssuesForUser(id: Int): Flow<List<Issue>>

    @Query("SELECT * FROM Issue WHERE id = :id")
    suspend fun getIssue(id: Int): Issue

    @Update
    suspend fun updateIssue(issue: Issue)
}
