package com.gubake.ui.pilihLawan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gubake.R
import com.gubake.ui.menu.MainMenuActivity
import kotlinx.android.synthetic.main.activity_pilih_lawan.*
import kotlinx.android.synthetic.main.activity_profile_teman.*
import kotlinx.android.synthetic.main.activity_profile_teman.recyclerView

class PilihLawan : AppCompatActivity() {
    private var presenter: PilihLawanPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_lawan)
        presenter = PilihLawanPresenterImp()
        btHome.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
            finish()
        }

        showLawan()

    }

    private fun showLawan(){
        presenter?.showList(recyclerView, this@PilihLawan)
    }

    override fun onResume() {
        super.onResume()
        showLawan()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroyDB()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainMenuActivity::class.java))
        finish()
    }

}