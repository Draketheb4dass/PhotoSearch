package com.jephte.photosearch

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PhotoSearchApp : Application() {
    companion object {
        lateinit var instance: PhotoSearchApp
            private set
    }
    override fun onCreate() {
        // Enable strict mode before Hilt create graph
        if (BuildConfig.DEBUG) enableStrictMode()

        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
    }


}