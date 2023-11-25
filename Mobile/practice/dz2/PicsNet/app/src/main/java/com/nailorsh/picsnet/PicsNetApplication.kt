package com.nailorsh.picsnet

import android.app.Application
import com.nailorsh.picsnet.data.AppContainer
import com.nailorsh.picsnet.data.DefaultAppContainer

class PicsNetApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}