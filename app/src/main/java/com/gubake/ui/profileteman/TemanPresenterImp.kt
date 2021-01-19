package com.gubake.ui.profileteman

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.gubake.utils.App
import com.gubake.utils.App.Companion.context
import com.gubake.utils.App.Companion.mDB
import com.gubake.data.local.SharedPref
import com.gubake.data.database.Teman
import com.gubake.data.database.TemanDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TemanPresenterImp(private val view: TemanView) : TemanPresenter {
    override fun playerName() {
        mDB = context?.let { TemanDatabase.getInstance(it) }
        GlobalScope.launch(Dispatchers.IO){
            val pemain = SharedPref.id?.let { mDB?.pemainDao()?.getPemainById(it) }
            launch(Dispatchers.Main) {
                val username=pemain?.username.toString()
                val email=pemain?.email.toString()
                view.nameEmail(username,email)
            }
        }
    }

    override fun addTeman(name: String, email: String) {
        mDB = context?.let { TemanDatabase.getInstance(it) }
        val id=SharedPref.id
        val objectTeman = id?.let { Teman(null, it,name, email) }
        GlobalScope.launch(Dispatchers.IO) {
           val result= objectTeman?.let { mDB?.temanDao()?.insertTeman(it) }
            launch(Dispatchers.Main) {
                if (result!=0.toLong()){
                    view.onSuccessTeman("Teman kamu $name berhasil ditambahakan")
                }
                else{
                    view.onFailedTeman("Teman kamu $name gagal ditambahakan")
                }
            }

        }
    }

    override fun listTeman(recyclerView: RecyclerView, context: Context) {
        val id=SharedPref.id
        mDB = App.context?.let { TemanDatabase.getInstance(it) }
        GlobalScope.launch (Dispatchers.IO){
            val listTeman = id?.let { mDB?.temanDao()?.getAllbyId(it) }
            launch(Dispatchers.Main){
                listTeman?.let {
                    val adapter = TemanAdapter(listTeman, context)
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    override fun deleteTeman(list: List<Teman>, position:Int) {
        GlobalScope.launch (Dispatchers.IO){
           val result= mDB?.temanDao()?.deleteTeman(list[position])
            launch (Dispatchers.Main){
                if (result != 0) {
                    view.onSuccessTeman("Teman kamu berhasil dihapus")
                } else {
                    view.onFailedTeman("Teman kamu gagal dihapus")
                }
            }
        }
    }

    override fun editTeman(list: List<Teman>, position: Int) {
        GlobalScope.launch (Dispatchers.IO){
            val result= mDB?.temanDao()?.updateTeman(list[position])
            launch(Dispatchers.Main) {
                if (result != 0) {
                    view.onSuccessTeman("Teman kamu berhasil diubah")
                } else {
                    view.onSuccessTeman("Teman kamu gagal diubah")
                }
            }
        }
    }


    override fun destroyDB() {
        TemanDatabase.destroyInstance()
    }
}