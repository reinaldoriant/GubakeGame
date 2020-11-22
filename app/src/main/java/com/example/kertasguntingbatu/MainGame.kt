package com.example.kertasguntingbatu

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.time.seconds
import kotlin.time.toDuration


class MainGame : AppCompatActivity(), IControllerNya {
    private lateinit var dataComp: String
    private lateinit var dataPlayer: String
    private val setImage by lazy {
        findViewById<ImageView>(R.id.imageBattle)
    }
    private val backAll by lazy {
        mutableListOf(
                findViewById(R.id.backgroundBatu),
                findViewById(R.id.backgroundScissors),
                findViewById<FrameLayout>(R.id.backgroundPaper),
                findViewById(R.id.backgroundBatuComp),
                findViewById(R.id.backgroundScissorsComp),
                findViewById(R.id.backgroundPaperComp),
        )
    }

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

                when (index) {
                    0 -> {
                        this.dataPlayer = "Batu"
                        this.dataComp = randomCompCl.randComp()
                        imageButton.startAnimation(scaleUp)
                        backAll[0].visibility = View.VISIBLE
                        dataModel(dataComp, dataPlayer)
                        backComp(dataComp)
                        imageButton.startAnimation(scaleDown)
                        imageButton.isEnabled = false
                    }
                    1 -> {
                        this.dataPlayer = "Gunting"
                        this.dataComp = randomCompCl.randComp()
                        imageButton.startAnimation(scaleUp)
                        backAll[1].visibility = View.VISIBLE
                        dataModel(dataComp, dataPlayer)
                        backComp(dataComp)
                        imageButton.startAnimation(scaleDown)
                        imageButton.isEnabled = false
                    }
                    2 -> {
                        dataPlayer = "Kertas"
                        this.dataComp = randomCompCl.randComp()
                        imageButton.startAnimation(scaleUp)
                        backAll[2].visibility = View.VISIBLE
                        dataModel(dataComp, dataPlayer)
                        backComp(dataComp)
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
        when (hasil) {
            "Player Menang" -> {
                setImage.setImageResource(R.drawable.ic_playerwinner)
            }
            "Player Kalah" -> {
                setImage.setImageResource(R.drawable.ic_compwinner)
            }
            else -> {
                setImage.setImageResource(R.drawable.ic_draw)

            }
        }
    }
    private fun backComp(compRes:String){
        when (compRes) {
            "Batu" -> {
                backAll[3].visibility = View.VISIBLE
            }
            "Gunting" -> {
                backAll[4].visibility = View.VISIBLE
            }
            "Kertas" -> {
                backAll[5].visibility = View.VISIBLE
            }
        }

    }
    private fun reset() {
        mutableListOf(
                R.id.batuPlayer,
                R.id.scissorsPlayer,
                R.id.paperPlayer,
        ).forEach {
            findViewById<ImageButton>(it).isEnabled = true
            backAll[0].visibility = View.INVISIBLE
            backAll[1].visibility = View.INVISIBLE
            backAll[2].visibility = View.INVISIBLE
            backAll[3].visibility = View.INVISIBLE
            backAll[4].visibility = View.INVISIBLE
            backAll[5].visibility = View.INVISIBLE
            setImage.setImageResource(R.drawable.ic_battle_image)
        }
    }

}



