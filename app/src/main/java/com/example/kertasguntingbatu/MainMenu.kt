package com.example.kertasguntingbatu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.kertasguntingbatu.playgame.MainGameComputer
import com.example.kertasguntingbatu.playgame.MainGamePlayer

class MainMenu : AppCompatActivity() {
    private val buttonPlay by lazy {
        mutableListOf(
            findViewById<ImageButton>(R.id.butPlayVsPlay), findViewById(R.id.butPlayVsComp)
        )
    }
    private val intent by lazy {
        mutableListOf(
            Intent(this@MainMenu, MainGamePlayer::class.java),
            Intent(this@MainMenu, MainGameComputer::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val butNya = mutableListOf(
            buttonPlay[0], buttonPlay[1]
        )
        butNya.forEachIndexed { _, imageButton ->
            imageButton.setOnClickListener {
                when (it) {
                    buttonPlay[0] -> {
                        intent[0]
                        intent[0].putExtra("NAME", "Aldo")
                        startActivity(intent[0])
                    }
                    buttonPlay[1] -> {
                        intent[1]
                        intent[1].putExtra("NAME", "Aldo")
                        startActivity(intent[1])
                    }
                }
            }
        }


    }
}