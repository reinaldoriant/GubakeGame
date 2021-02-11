package com.gubake.data.local

import android.content.Context
import com.gubake.utils.App

object SharedPref {
    private const val KEY_ISLOGIN = "KEY_ISLOGIN"
    private const val KEY_ID = "KEY_ID"
    private const val KEY_EMAIL = "KEY_EMAIL"
    private const val KEY_TOKEN = "KEY_TOKEN"
    private const val KEY_USERNAME = "KEY_USERNAME"
    private const val KEY_PASSWORD = "KEY_PASSWORD"
    private const val DATETIME_LOGIN = "DATETIME_LOGIN"

    private val pref = App.context.get()?.getSharedPreferences("CodeChallenge7", Context.MODE_PRIVATE)
    var id: String?
        get()  = pref?.getString(KEY_ID, "")
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putString(KEY_ID, it)
                    ?.apply()
            }
        }

    var email: String?
        get() = pref?.getString(KEY_EMAIL, "")
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putString(KEY_EMAIL, it)
                    ?.apply()
            }
        }
    var token: String?
        get() = pref?.getString(KEY_TOKEN, "")
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putString(KEY_TOKEN, it)
                    ?.apply()
            }
        }
    var username: String?
        get() = pref?.getString(KEY_USERNAME, "")
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putString(KEY_USERNAME, it)
                    ?.apply()
            }
        }
    var password: String?
        get() = pref?.getString(KEY_PASSWORD, "")
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putString(KEY_PASSWORD, it)
                    ?.apply()
            }
        }
    var datetime_login: String?
        get() = pref?.getString(DATETIME_LOGIN, "")
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putString(DATETIME_LOGIN, it)
                    ?.apply()
            }
        }
    var isLogin: Boolean?
        get() = pref?.getBoolean(KEY_ISLOGIN, false)
        set(value) {
            value?.let{
                pref?.edit()
                    ?.putBoolean(KEY_ISLOGIN, it)
                    ?.apply()
            }
        }

}