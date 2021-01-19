package com.gubake.utils

import android.app.Application
import android.content.Context
import com.gubake.data.database.TemanDatabase

class App: Application() {
    companion object{
        var context: Context? = null
        var mDB: TemanDatabase?=null
    }

    override fun onCreate() {
        super.onCreate()
        context =applicationContext
    }
}