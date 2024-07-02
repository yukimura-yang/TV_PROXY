package moe.gkd.tvproxy

import android.app.Application

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ShizukuSettings.initialize(this)
    }
}