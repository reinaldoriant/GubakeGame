package com.gubake.ui.signUp

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gubake.data.remote.ApiService
import com.gubake.data.model.SignUpRequest
import com.gubake.utils.getServiceErrorMsg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern

class SignUpViewModel(private val service: ApiService) : ViewModel() {
    private val usernameRegex =
        Pattern.compile("^(?=.{6,20}\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![@#\$%^&+=_.])\$")
    private val passwordRegex = Pattern.compile(
        "^"
                + "(?=.*[0-9])" // at least 1 digit
                + "(?=.*[a-z])"// at least 1 lower case letter
                + "(?=.*[A-Z])"// at least 1 upper case letter
                + "(?=.*[@#$%^&+=_.])"// no white spaces
                + ".{6,}" //at least 6 characters
                + "$"
    )
    private var disposable: Disposable? = null
    private val errorMsg = MutableLiveData<String>()
    private val typeError = MutableLiveData<String>()
    private val buttonResult = MutableLiveData<String>()
    private val resultSignUp = MutableLiveData<Boolean>()
    var username: String = ""
    var password: String = ""
    var email: String = ""
    var rePassword: String = ""
    fun errorMsg(): LiveData<String> = errorMsg
    fun buttonResult(): LiveData<String> = buttonResult
    fun typeError(): LiveData<String> = typeError
    fun resultLogin(): LiveData<Boolean> = resultSignUp

    fun signUp() {
        mutableListOf(username, email, password, rePassword).forEachIndexed { index, s ->
            when {
                s.isEmpty() -> {
                    when (index) {
                        0 -> {
                            errorMsg.value = "Username tidak boleh kosong!"
                            resultSignUp.value = true
                            buttonResult.value = "Signup"
                            typeError.value = "username"
                        }
                        1 -> {
                            errorMsg.value = "Email tidak boleh kosong!"
                            resultSignUp.value = true
                            buttonResult.value = "Signup"
                            typeError.value = "email"
                        }
                        2 -> {
                            errorMsg.value = "Password tidak boleh kosong!"
                            resultSignUp.value = true
                            buttonResult.value = "Signup"
                            typeError.value = "password"
                        }
                        3 -> {
                            errorMsg.value = "Re-Password tidak boleh kosong!"
                            resultSignUp.value = true
                            buttonResult.value = "Signup"
                            typeError.value = "repassword"
                        }
                    }
                }
                index == 0 -> {
                    if (!usernameRegex.matcher(username).matches()) {
                        errorMsg.value = "Harus lebih dari 5 (a-z / 0-9)"
                        resultSignUp.value = true
                        buttonResult.value = "Signup"
                        typeError.value = "username"
                    }
                }
                index == 1 -> {
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorMsg.value = "Email tidak valid!"
                        resultSignUp.value = true
                        buttonResult.value = "Signup"
                        typeError.value = "email"
                    }
                }

                else -> {
                    if (!passwordRegex.matcher(password).matches()) {
                        errorMsg.value = "Password terlalu lemah"
                        resultSignUp.value = true
                        buttonResult.value = "Signup"
                        typeError.value = "password"
                    } else if (password == rePassword) {
                        val data = SignUpRequest(email, password, username)
                        disposable = service.signUp(data)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                if (it.success) {
                                    resultSignUp.value = false
                                }
                            }, {
                                val msg = it.getServiceErrorMsg()
                                when {
                                    msg.contains("username_1 dup key") -> {
                                        errorMsg.value = "Username telah digunakan"
                                        resultSignUp.value = true
                                        buttonResult.value = "Signup"
                                        typeError.value = "username"
                                    }
                                    msg.contains("email_1 dup key") -> {
                                        errorMsg.value = "Email telah digunakan"
                                        resultSignUp.value = true
                                        buttonResult.value = "Signup"
                                        typeError.value = "email"
                                    }
                                    msg.contains("should only contain alphanumeric characters") -> {
                                        errorMsg.value = "Username harus berisi alphanumeric"
                                        resultSignUp.value = true
                                        buttonResult.value = "Signup"
                                        typeError.value = "username"
                                    }
                                }
                            })
                    } else {
                        errorMsg.value = "Re-Password berbeda dengan password"
                        resultSignUp.value = true
                        buttonResult.value = "Signup"
                        typeError.value = "repassword"
                    }
                }
            }
        }
    }

}