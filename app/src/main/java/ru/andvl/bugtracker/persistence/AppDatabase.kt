package ru.andvl.bugtracker.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.andvl.bugtracker.model.Project
import ru.andvl.bugtracker.model.User

@Database(entities = [User::class, Project::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}