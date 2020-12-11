package com.example.kertasguntingbatu

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.kertasguntingbatu.playGame.MainGameComputer
import com.example.kertasguntingbatu.playGame.MainGamePlayer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main_menu.*


class MainMenu : AppCompatActivity() {
    private val buttonPlay by lazy {
        mutableListOf(
            findViewById<ImageButton>(R.id.butPlayVsPlay), findViewById(R.id.butPlayVsComp)
        )
    }
    private val intentNya by lazy {
        mutableListOf(
            Intent(this@MainMenu, MainGamePlayer::class.java),
            Intent(this@MainMenu, MainGameComputer::class.java)
        )
    }
    private var namePlay:String="Pemain1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        namePlayerComp.text = intent.extras?.getString("dataName")
        namePlayerPlayer.text = intent.extras?.getString("dataName")
        namePlay= intent.extras?.getString("dataName").toString()
        val butNya = mutableListOf(
            buttonPlay[0], buttonPlay[1]
        )
        butNya.forEachIndexed { _, imageButton ->
            imageButton.setOnClickListener {
                when (it) {
                    buttonPlay[0] -> {
                        intentNya[0]
                        intentNya[0].putExtra("dataName", namePlay)
                        startActivity(intentNya[0])
                    }
                    buttonPlay[1] -> {
                        intentNya[1]
                        intentNya[1].putExtra("dataName", namePlay)
                        startActivity(intentNya[1])
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val snackPlayer= Snackbar.make(mainMenu, "Selamat Datang $namePlay", Snackbar.LENGTH_SHORT)
        snackPlayer.setAction("Tutup") {
            snackPlayer.dismiss()
            finish()
        }.show()
    }
}