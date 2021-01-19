package com.gubake.ui.signup

interface SignUpView {
    fun onSuccess()
    fun onError(msg: String)
}