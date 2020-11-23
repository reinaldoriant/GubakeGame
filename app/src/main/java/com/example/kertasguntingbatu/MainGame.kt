package com.example.kertasguntingbatu

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


class MainGame : AppCompatActivity(), IControllerNya {

    private var dataPlayer = ""
    private val setImage by lazy {
        findViewById<ImageView>(R.id.imageBattle)
    }
    private val backAll by lazy {
        mutableListOf<FrameLayout>(
                findViewById(R.id.backgroundBatu),
                findViewById(R.id.backgroundScissors),
                findViewById(R.id.backgroundPaper),
                findViewById(R.id.backgroundBatuComp),
                findViewById(R.id.backgroundScissorsComp),
                findViewById(R.id.backgroundPaperComp),
        )
    }
    private val buttonAll by lazy {
        mutableListOf<ImageButton>(findViewById(R.id.batuPlayer),findViewById(R.id.scissorsPlayer),
                findViewById(R.id.paperPlayer),
                findViewById(R.id.refreshBut)
        )
    }
    private val controller = ControllerNya(this)
    private val randDuration = 100L
    private var randNum = 0

    /*private var randomCompCl = RandomComp()*/
    override fun onCreate(savedInstanceState: Bundle?) {
        /*supportActionBar?.hide()*/
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maingame)
        reset()
        mutableListOf(
                buttonAll[0],
                buttonAll[1],
                buttonAll[2],
        ).forEachIndexed { _, imageButton ->
            imageButton.setOnClickListener {
                when (it) {
                    buttonAll[0] -> {
                        if (!backAll[0].isVisible) {
                            backAll[0].visibility = View.VISIBLE

                        }
                        dataPlayer = "Batu"
                    }
                    backAll[1] -> {
                        if (!backAll[1].isVisible) {
                            backAll[1].visibility = View.VISIBLE
                        }
                        dataPlayer = "Gunting"
                    }
                    backAll[2] -> {
                        if (!backAll[2].isVisible) {
                            backAll[2].visibility = View.VISIBLE
                        }
                        dataPlayer = "Kertas"
                    }
                }
                Log.i("MainActivity", "memilih $this.dataPlayer")

                lockButton()
                animationRandLoop()
            }
        }
        buttonAll[3].setOnClickListener {
            reset()
        }

    }

    private fun animationRandLoop() {
        mutableListOf(backAll[3], backAll[4], backAll[5])
                .forEachIndexed { _, i ->
                    Handler().postDelayed({
                        if (i.isVisible) {
                            i.visibility = View.INVISIBLE
                            Log.i("MainGame", "Hilang Sejenak Background nya Computer")
                        }
                    }, randDuration)
                }
        Handler().postDelayed({
            if (randNum <= 30) {
                controller.compRand()
                Log.i("MainActivity", "Perulangan Computer #${randNum}")
            } else {
                dataModel()
                randNum = 0
            }
        }, randDuration)
    }

    private fun dataModel() {

        val dataMauPlayer = ModelNya(dataPlayer)
        Log.i("MainActivity", "Proses Suit Computer vs Pemain")
        controller.setDataPlayer(dataMauPlayer)
        controller.compProcess()
        controller.compareData()
    }
    private fun lockButton() {
        buttonAll[0].isEnabled = false
        buttonAll[1].isEnabled = false
        buttonAll[2].isEnabled = false
        Log.i("MainGame", "di Lock yekkk ga bisa main kapok kon")
    }

    private fun unlockButton() {
        if (!buttonAll[0].isEnabled) {
            buttonAll[0].isEnabled = true
        } else if (!buttonAll[1].isEnabled) {
            buttonAll[1].isEnabled = true
        } else if (!buttonAll[2].isEnabled) {
            buttonAll[2].isEnabled = true
        }
        Log.i("MainGame", "Cieee bisa buka lagi")
    }
    private fun reset() {
        mutableListOf(
                backAll[0],
                backAll[1],
                backAll[2],
                backAll[3],
                backAll[4],
                backAll[5],
        ).forEachIndexed { _, i ->
            if (!i.isVisible) {
                i.visibility = View.INVISIBLE
                Log.i("MainGame", "Background nya ilang semua")
                setImage.setImageResource(R.drawable.ic_battle_image)
            }

        }
        mutableListOf(llPemain1Win, llPemain2Win, llDraw)
                .forEachIndexed { index, i ->
                    i.animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).rotation(-180f).setDuration(0)
                            .start()
                }
        llVS.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(1000).start()
        Log.e("MainActivity", "Make Win Label Alpha and VS label no Alpha")
        pilihan1 = ""
        unlookButton()
        ivReset.animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).rotation(180f).setDuration(1000)
                .start()
        tvPemain1.animate().scaleX(1f).scaleY(1f).translationY(0f).translationX(0f)
                .setDuration(1000).start()
        tvPemain2.animate().scaleX(1f).scaleY(1f).translationY(0f).translationX(0f)
                .setDuration(1000).start()
        dataPlayer = ""
        unlockButton()
    }
    override fun randAnim(animResult: String) {
        when (animResult) {
            "batu" -> {
                backAll[0].visibility = View.VISIBLE
                Log.i("MainGame", "Waw ada batu")
            }
            "gunting" -> {
                backAll[1].visibility = View.VISIBLE
                Log.i("MainGame", "Hmm gunting")
            }
            "kertas" -> {
                backAll[2].visibility = View.VISIBLE
                Log.i("MainGame", "Whoa ada kertas")
            }
        }
        randNum++
        animationRandLoop()
    }


    override fun resultRandom(resultRand: String) {
        when(resultRand){
            "batu" -> backAll[3].visibility = View.VISIBLE
            "gunting" -> backAll[4].visibility = View.VISIBLE
            "kertas" -> backAll[5].visibility = View.VISIBLE
        }
    }

    override fun result(resultNya: String) {
        when (resultNya) {
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





}



