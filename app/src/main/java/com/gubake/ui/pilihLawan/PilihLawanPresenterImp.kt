package com.gubake.ui.pilihLawan

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gubake.data.database.TemanDatabase
import com.gubake.data.local.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PilihLawanPresenterImp : PilihLawanPresenter {
    override fun showList(recyclerView: RecyclerView, context: Context) {
        val mDB = TemanDatabase.getInstance(context)
        recyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        GlobalScope.launch(Dispatchers.IO) {
            val id = SharedPref.id
            val listTeman = id?.let { mDB?.temanDao()?.getAllbyId(it) }
            launch(Dispatchers.Main) {
                listTeman?.let {
                    val adapter = PilihLawanAdapter(listTeman, context)
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    override fun destroyDB() {
        TemanDatabase.destroyInstance()
    }
}