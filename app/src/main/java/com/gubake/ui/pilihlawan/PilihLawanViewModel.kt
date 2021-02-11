package com.gubake.ui.pilihlawan

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.gubake.data.database.TemanDatabase
import com.gubake.data.local.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PilihLawanViewModel(
    private val mDB: TemanDatabase,
    private val pref: SharedPref
) : ViewModel() {
    fun showList(recyclerView: RecyclerView, context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val idPlayer = pref.id
            val listTeman = idPlayer?.let { mDB.temanDao().getAllbyId(it) }
            launch(Dispatchers.Main) {
                listTeman?.let {
                    val adapter = PilihLawanAdapter(listTeman, context)
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
            return PilihLawanViewModel(mDB, pref) as T
        }
    }
}