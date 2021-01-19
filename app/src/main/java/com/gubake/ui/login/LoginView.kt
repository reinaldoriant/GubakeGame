package com.gubake.ui.login

interface LoginView {
    fun onSuccess()
    fun onError(msg: String)

}