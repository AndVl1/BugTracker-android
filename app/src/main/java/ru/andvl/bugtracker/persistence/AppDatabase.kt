package ru.andvl.bugtracker.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.andvl.bugtracker.model.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}