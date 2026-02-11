package com.parimal.kotlin_multiplatform

import android.app.Application
import com.parimal.kotlin_multiplatform.dependency_injection.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MyApplication)   // needed in future
        }

    }

}