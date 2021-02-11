package com.gubake.utils

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.gubake.data.local.SharedPref
import com.gubake.data.model.LoginRequest
import com.gubake.data.remote.ApiModule.service
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

private lateinit var disposable: Disposable
fun refreshToken() {
    val tag = "Handling"
    val mainHandler = Handler(Looper.getMainLooper())
    val email = SharedPref.email.toString()
    val password = SharedPref.password.toString()

    class Runnable1 : Runnable {
        override fun run() {
            GlobalScope.launch(Dispatchers.IO) {
                val runCalendar = Calendar.getInstance()
                val rTimer = runCalendar.time.time
                if ((SharedPref.datetime_login)!!.toLong() < rTimer) {
                    calendar()
                    val requestLogin = LoginRequest(email, password)
                    disposable = service.loginAction(requestLogin)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            SharedPref.token = ("Bearer" + " " + it.data.token)
                            Log.e(tag, it.data.token)
                            Log.e(tag, "Cobaan @@@@@@@")
                        }) {
                            it.getServiceErrorMsg()
                            it.printStackTrace()
                        }
                }
            }
            mainHandler.postDelayed(this, 2000)
        }
    }
    mainHandler.post(Runnable1())
}

fun calendar() {
    GlobalScope.launch(Dispatchers.IO) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, 30)
        val dLogin: Long = calendar.time.time
        SharedPref.datetime_login = dLogin.toString()
    }
}

