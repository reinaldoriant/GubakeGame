package com.gubake.ui.mainMenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gubake.data.local.SharedPref
import com.gubake.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainMenuViewModel(private val service: ApiService) : ViewModel() {
    private lateinit var disposable: Disposable
    private val username = MutableLiveData<String>()
    private val imageResult = MutableLiveData<String>()
    fun imageResult(): LiveData<String> = imageResult
    fun username():LiveData<String> = username
    fun getUser() {
       val  token= SharedPref.token.toString()
        disposable = service.getUsers(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                imageResult.value=it.data.photo
                username.value=("Hi, ${it.data.username}")
            }, {
                it.printStackTrace()
            })
    }


}


