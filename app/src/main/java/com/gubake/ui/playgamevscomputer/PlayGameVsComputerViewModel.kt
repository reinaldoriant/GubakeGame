package com.gubake.ui.playgamevscomputer

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gubake.data.local.SharedPref
import com.gubake.data.model.PostBattleRequest
import com.gubake.data.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PlayGameVsComputerViewModel(
    private val service: ApiService,
    pref: SharedPref
) : ViewModel() {

    private val tag = "PlayGameVsComputer"

    private val token = pref.token.toString()
    private  var disposable: Disposable?=null
    val resultAnim = MutableLiveData<String>()
    val resultEnd = MutableLiveData<String>()
    val resultNya = MutableLiveData<String>()
    private var dataCompRand = mutableListOf("batu", "gunting", "kertas")
    private var randNum = 0

    fun animAcakLoop() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (randNum <= 35) {
                resultAnim.value = dataCompRand.random()
                randNum++
                Log.e(tag, "Perulangan Computer #${randNum}")
                animAcakLoop()
            } else {
                resultEnd.value = dataCompRand.random()
                randNum = 0
            }
        }, 400L)
    }

    fun compareData(dataPlayer1: String, dataPlayer2: String) {
        //dataPlayer  Menang
        resultNya.value = if (dataPlayer1 == "batu" && dataPlayer2 == "gunting" ||
            dataPlayer1 == "kertas" && dataPlayer2 == "batu" ||
            dataPlayer1 == "gunting" && dataPlayer2 == "kertas"
        ) {
            "Player Win"
        }
        //data comp Menang
        else if (dataPlayer2 == "batu" && dataPlayer1 == "gunting" ||
            dataPlayer2 == "kertas" && dataPlayer1 == "batu" ||
            dataPlayer2 == "gunting" && dataPlayer1 == "kertas"
        ) {
            "Opponent Win"
        } else {
            "Draw"
        }
        simpanBattle()
    }

    private fun simpanBattle() {
        val postBattleRequest = PostBattleRequest("Singleplayer", resultNya.value.toString())
        disposable = service.postBattle(token, postBattleRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val service: ApiService,
        private val pref: SharedPref
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PlayGameVsComputerViewModel(service, pref) as T
        }
    }
}
