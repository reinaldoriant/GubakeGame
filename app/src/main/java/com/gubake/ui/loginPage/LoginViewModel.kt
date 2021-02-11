package com.gubake.ui.loginPage

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gubake.data.local.SharedPref
import com.gubake.data.model.LoginRequest
import com.gubake.data.remote.ApiService
import com.gubake.utils.calendar
import com.gubake.utils.getServiceErrorMsg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val service: ApiService) : ViewModel() {
    private lateinit var disposable: Disposable
    private val errorMsg = MutableLiveData<String>()
    private val typeError = MutableLiveData<String>()
    private val buttonResult = MutableLiveData<String>()
    private val resultLogin = MutableLiveData<Boolean>()
    var username: String = ""
    var password: String = ""
    fun errorMsg(): LiveData<String> = errorMsg
    fun buttonResult(): LiveData<String> = buttonResult
    fun typeError(): LiveData<String> = typeError
    fun resultLogin(): LiveData<Boolean> = resultLogin
    fun login() {
        mutableListOf(password, username).forEachIndexed { index, s ->
            when {
                s.isEmpty() -> {
                    when (index) {
                        0 -> {
                            errorMsg.value = "Password tidak boleh kosong!"
                            typeError.value = "password"
                            resultLogin.value = true
                            buttonResult.value = "Signin"
                        }
                        1 -> {
                            errorMsg.value = "Email tidak boleh kosong!"
                            typeError.value = "email"
                            resultLogin.value = true
                            buttonResult.value = "Signin"
                        }
                    }
                }
                index == 0 -> {
                    if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                        errorMsg.value = "Email tidak valid!"
                        typeError.value = "email"
                        resultLogin.value = true
                        buttonResult.value = "Signin"
                    }
                }
                else -> {
                    calendar()
                    val loginRequest = LoginRequest(username, password)
                    disposable = service.loginAction(loginRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            SharedPref.id = it.data.id
                            SharedPref.email = it.data.email
                            SharedPref.username = it.data.username
                            SharedPref.token = ("Bearer" + " " + it.data.token)
                            SharedPref.password = password
                            SharedPref.isLogin = true
                            resultLogin.value = false
                        }, {
                            val msg = it.getServiceErrorMsg()
                            Log.e("Opo error e?", msg)
                            if (msg == "Email doesn't exist!") {
                                errorMsg.value = "Email tidak ada!"
                                typeError.value = "email"
                                resultLogin.value = true
                                buttonResult.value = "Signin"
                            } else if (msg == "Wrong password!") {
                                errorMsg.value = "Password salah!"
                                typeError.value = "password"
                                resultLogin.value = true
                                buttonResult.value = "Signin"

                            }
                        }
                        )
                }
            }
        }
    }
}
