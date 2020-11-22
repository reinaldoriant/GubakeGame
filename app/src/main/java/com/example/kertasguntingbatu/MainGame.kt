package com.example.kertasguntingbatu

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainGame : AppCompatActivity(), IControllerNya {
    private lateinit var dataComp: String
    private lateinit var dataPlayer: String
    private var randomCompCl = RandomComp()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maingame)
        mutableListOf(
                R.id.batuPlayer,
                R.id.scissorsPlayer,
                R.id.paperPlayer,
                R.id.refreshBut
        ).forEachIndexed { index, i ->
            findViewById<ImageButton>(i).setOnClickListener { parameter ->
                val imageButton: ImageButton = parameter as ImageButton
                val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scaleup_size)
                val scaleDown = AnimationUtils.loadAnimation(this, R.anim.scaledown_size)
                mutableListOf(R.id.backgroundBatu, R.id.backgroundPaper, R.id.backgroundScissors
                ).forEachIndexed{ index, i -> findViewById<FrameLayout>(i).visibility
                }
                val backRock = findViewById<FrameLayout>(R.id.backgroundScissors)
                when (index) {
                    0 -> {
                        this.dataPlayer = "Batu"
                        this.dataComp = randomCompCl.randComp()
                        imageButton.startAnimation(scaleUp)
                        backRock.visibility = View.VISIBLE
                        dataModel(dataComp, dataPlayer)
                        imageButton.startAnimation(scaleDown)
                        imageButton.isEnabled = false
                    }
                    1 -> {
                        this.dataPlayer = "Gunting"
                        this.dataComp = randomCompCl.randComp()
                        imageButton.startAnimation(scaleUp)
                        dataModel(dataComp, dataPlayer)
                        imageButton.startAnimation(scaleDown)
                        imageButton.isEnabled = false
                    }
                    2 -> {
                        dataPlayer = "Kertas"
                        this.dataComp = randomCompCl.randComp()
                        imageButton.startAnimation(scaleUp)
                        dataModel(dataComp, dataPlayer)
                        imageButton.startAnimation(scaleDown)
                        imageButton.isEnabled = false
                    }
                    3 -> {
                        imageButton.startAnimation(scaleUp)
                        reset()
                        imageButton.startAnimation(scaleDown)
                    }
                }

            }
        }
    }


    private fun dataModel(dataComp: String, dataPlay: String) {
        val controller = ControllerNya(this)
        val dataMauPlayer = ModelNya(dataPlay)
        val dataMauComp = ModelnyaComp(dataComp)

        controller.setDataPlayer(dataMauPlayer)
        controller.setDataComp(dataMauComp)
        controller.compareData()

    }

    override fun result(hasil: String) {
        val startImage = findViewById<ImageView>(R.id.imageBattle)
        if (hasil == "Player Menang") {

        }
        Toast.makeText(this, "$dataPlayer $dataComp $hasil", Toast.LENGTH_SHORT).show()
    }

    private fun reset() {
        mutableListOf(
                R.id.batuPlayer,
                R.id.scissorsPlayer,
                R.id.paperPlayer,
        ).forEach {
            findViewById<Button>(it).isEnabled = true
        }
    }

}



