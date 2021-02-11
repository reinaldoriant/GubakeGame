package com.gubake.ui.profileplayer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gubake.data.local.SharedPref
import com.gubake.data.model.Users
import com.gubake.utils.getServiceErrorMsg
import com.gubake.data.remote.ApiModule.service
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProfilePlayerViewModel(
    private val pref: SharedPref
) : ViewModel() {

    private var tag = "ProfilePlayer"
    private lateinit var disposable: Disposable
    var resultUser = MutableLiveData<Users>()
    var resultMessage = MutableLiveData<String>()
    
    fun playerData() {
        val token = pref.token.toString()
        disposable = service.getUsers(token)
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

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val pref: SharedPref
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfilePlayerViewModel(pref) as T
        }
    }
}
