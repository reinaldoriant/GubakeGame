package com.gubake.ui.gamehistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gubake.data.local.SharedPref
import com.gubake.data.model.GetBattle
import com.gubake.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GameHistoryViewModel(private val service: ApiService):ViewModel() {
    private var disposable: Disposable? = null
    private val token = SharedPref.token.toString()
    var resultScore = MutableLiveData<List<GetBattle.Data>>()
    fun listScore() {

        disposable = service.getBattle(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resultScore.value=it.data
            }, {
                it.printStackTrace()
            })
    }
    @Suppress("UNCHECKED_CAST")
    class Factory(private val service: ApiService) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GameHistoryViewModel(service) as T
        }
    }
}