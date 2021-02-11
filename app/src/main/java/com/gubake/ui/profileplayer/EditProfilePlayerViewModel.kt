package com.gubake.ui.profileplayer

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gubake.data.local.SharedPref
import com.gubake.data.model.Users
import com.gubake.data.remote.ApiModule
import com.gubake.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import com.gubake.utils.getServiceErrorMsg
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.regex.Pattern

class EditProfilePlayerViewModel(
    private val service: ApiService,
    private val pref: SharedPref
) : ViewModel() {

    val token = pref.token.toString()
    private val tag: String = "EditProfilePlayer"
    private val usernameRegex =
        Pattern.compile("^(?=.{6,20}\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![@#\$%^&+=_.])\$")
    private lateinit var disposable1: Disposable
    private var disposable: CompositeDisposable = CompositeDisposable()
    val resultPost = MutableLiveData<Boolean>()
    var resultUser = MutableLiveData<Users>()
    var resultMessage = MutableLiveData<String>()

    fun playerData() {
        val token = pref.token.toString()
        disposable1 = ApiModule.service.getUsers(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    SharedPref.email = it.data.email
                    SharedPref.username = it.data.username
                    resultUser.value = it
                },
                {
                    val msg: String = it.getServiceErrorMsg()
                    Log.e(tag, msg)
                    if (msg == "Token is expired" || msg == "Invalid Token") {
                        resultMessage.value = msg
                    }
                    it.printStackTrace()
                })
    }

    fun upload(username: String, email: String, file: File) {
        Log.e(tag, "upload?")
        if (!usernameRegex.matcher(username).matches()) {
            resultMessage.value = "username weak"
            resultPost.value = false
        } else if (email.isEmpty()) {
            resultMessage.value = "email empty"
            resultPost.value = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            resultMessage.value = "email no valid"
            resultPost.value = false
        } else {
            val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                file.asRequestBody("image/*".toMediaTypeOrNull())
            )
            val usernamePart: RequestBody =
                username.toRequestBody("multipart/form-data".toMediaType())
            val emailPart: RequestBody = email.toRequestBody("multipart/form-data".toMediaType())

            disposable.addAll(
                service.uploadImage(token, usernamePart, emailPart, filePart)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        resultPost.value = true
                        pref.email = it.data.email
                        pref.username = it.data.username
                        Log.e(tag, "datasaved")
                        resultMessage.value = "data diperbaharui"
                    }, {
                        resultPost.value = false
                        val msg: String = it.getServiceErrorMsg()
                        Log.e(tag, msg)
                        if (msg == "Token is expired" || msg == "Invalid Token") {
                            resultMessage.value = msg
                        }
                        it.printStackTrace()
                    })
            )

        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val service: ApiService,
        private val pref: SharedPref
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EditProfilePlayerViewModel(service, pref) as T
        }
    }
}
