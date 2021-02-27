package com.gubake.utils

import android.app.Application
import android.content.Context
import com.gubake.di.apiModule
import com.gubake.di.dbModule
import com.gubake.di.repositoryModule
import com.gubake.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import java.lang.ref.WeakReference

class App: Application() {
    companion object{
        lateinit  var context : WeakReference<Context>
    }

    override fun onCreate() {
        super.onCreate()
        context = WeakReference(applicationContext)
        startKoin {
            androidLogger()
            context.get()?.let { androidContext(it) }
            loadKoinModules(listOf(uiModule, repositoryModule, apiModule, dbModule))
        }
    }
}