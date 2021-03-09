package ru.andvl.bugtracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.andvl.bugtracker.model.User
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /* TODO Timber.plant() for RELEASE */
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    companion object {
        var currentUser = User(0, "", "")
    }
}
