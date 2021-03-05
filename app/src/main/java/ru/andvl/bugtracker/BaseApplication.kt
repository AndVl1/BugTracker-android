package ru.andvl.bugtracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
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
}
