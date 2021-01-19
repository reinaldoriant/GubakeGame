package com.gubake.ui.profileteman

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.gubake.data.database.Teman

interface TemanPresenter {
    fun playerName()
    fun addTeman(name: String, email: String)
    fun listTeman(recyclerView: RecyclerView, context: Context)
    fun deleteTeman(list:List<Teman>, position: Int)
    fun editTeman(list:List<Teman>, position: Int)

    fun destroyDB()
}