package ru.andvl.bugtracker.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.andvl.bugtracker.model.Project

@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProject(project: Project)

    @Query("SELECT * FROM Project")
    fun getProjects(): Flow<List<Project>>

    @Query("SELECT * FROM Project WHERE id=:id")
    fun getProject(id: Int): Flow<Project>
}
