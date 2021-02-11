package com.gubake.ui.profileteman

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.gubake.data.database.Teman
import com.gubake.data.database.TemanDatabase
import com.gubake.data.local.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileTemanViewModel(
    private val mDB: TemanDatabase,
    private val pref: SharedPref
) : ViewModel() {

    private val idPlayer = pref.id
    var resultName = MutableLiveData<String>()
    var resultEmail = MutableLiveData<String>()

    fun playerName() {
        resultName.value = pref.username.toString()
        resultEmail.value = pref.email.toString()
    }

    var resultAddTeman = MutableLiveData<String>()
    fun addTeman(name: String, email: String) {
        val objectTeman = idPlayer?.let { Teman(null, it, name, email) }
        GlobalScope.launch(Dispatchers.IO) {
            val result = objectTeman?.let { mDB.temanDao().insertTeman(it) }
            launch(Dispatchers.Main) {
                if (result != 0.toLong()) {
                    resultAddTeman.value = "Teman kamu $name berhasil ditambahakan"
                } else {
                    resultAddTeman.value = "Teman kamu $name gagal ditambahakan"
                }
            }
        }
    }

    fun listTeman(recyclerView: RecyclerView, context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val listTeman = idPlayer?.let { mDB.temanDao().getAllbyId(it) }
            launch(Dispatchers.Main) {
                listTeman?.let {
                    val adapter = TemanAdapter(listTeman, context)
                    adapter.notifyDataSetChanged()
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    fun destroyDB() {
        TemanDatabase.destroyInstance()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val mDB: TemanDatabase,
        private val pref: SharedPref
        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileTemanViewModel(mDB, pref) as T
        }
    }
}