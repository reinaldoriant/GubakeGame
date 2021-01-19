package com.gubake.ui.pilihLawan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gubake.R
import com.gubake.data.database.TemanDatabase
import kotlinx.android.synthetic.main.activity_pilih_lawan.*
import kotlinx.android.synthetic.main.activity_profile_teman.*
import kotlinx.android.synthetic.main.activity_profile_teman.recyclerView
import kotlinx.android.synthetic.main.activity_profile_teman.tvDaftar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DaftarPemain : AppCompatActivity() {
    private var mDB : TemanDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_lawan)

        tvDaftar.text = ("Daftar Pemain")
        mDB = TemanDatabase.getInstance(this)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false)
        fetchData()
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData(){
        GlobalScope.launch {
            val listPemain = mDB?.pemainDao()?.getAllPemain()
            runOnUiThread {
                listPemain?.let {
                    val adapter = DaftarPemainAdapter(listPemain, this@DaftarPemain)
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TemanDatabase.destroyInstance()
    }
}