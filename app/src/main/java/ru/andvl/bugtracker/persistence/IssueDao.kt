package ru.andvl.bugtracker.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.andvl.bugtracker.model.Issue

@Dao
interface IssueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIssue(issue: Issue)

    @Query("SELECT * FROM Issue")
    fun getIssues(): Flow<List<Issue>>

    @Query("SELECT * FROM Issue WHERE id = :id")
    fun getIssue(id: Int): Flow<Issue>
}
