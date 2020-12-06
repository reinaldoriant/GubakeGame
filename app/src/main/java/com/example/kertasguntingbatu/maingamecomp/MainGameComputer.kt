package com.example.kertasguntingbatu.maingamecomp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.kertasguntingbatu.R


class MainGameComputer : AppCompatActivity(), IControllerNya {
    private var dataPlayer = ""
    private val setImage by lazy {
        findViewById<ImageView>(R.id.imageBattle)
    }
    private val backAll by lazy {
        mutableListOf(
                findViewById<FrameLayout>(R.id.backgroundBatu), findViewById(R.id.backgroundScissors),
                findViewById(R.id.backgroundPaper), findViewById(R.id.backgroundBatuComp),
                findViewById(R.id.backgroundScissorsComp), findViewById(R.id.backgroundPaperComp),
        )
    }
    private val buttonAll by lazy {
        mutableListOf(
                findViewById(R.id.batuPlayer), findViewById(R.id.scissorsPlayer),
                findViewById(R.id.paperPlayer), findViewById<ImageButton>(R.id.refreshBut)
        )
    }
    private val resultImg by lazy {
        mutableListOf<ImageView>(
                findViewById(R.id.imageBattle), findViewById(R.id.imagePlayerWin),
                findViewById(R.id.imageCompWin), findViewById(R.id.imageDraw)
        )
    }
    private val controller = ControllerNyaComp(this)
    private val randDuration = 200L
    private var randNum = 0

    /*private var randomCompCl = RandomComp()*/
    override fun onCreate(savedInstanceState: Bundle?) {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maingame_comp)
        reset()
        val butNya = mutableListOf(
                buttonAll[0], buttonAll[1], buttonAll[2],
        )
        butNya.forEachIndexed { _, imageButton ->
            imageButton.setOnClickListener {
                when (it) {
                    buttonAll[0] -> {
                        if (!backAll[0].isVisible) {
                            buttonAll[0].startAnimation(animation)
                            backAll[0].visibility = View.VISIBLE

                        }
                        this.dataPlayer = "Batu"
                    }
                    buttonAll[1] -> {
                        if (!backAll[1].isVisible) {
                            buttonAll[1].startAnimation(animation)
                            backAll[1].visibility = View.VISIBLE
                        }
                        this.dataPlayer = "Gunting"
                    }
                    else -> {
                        if (!backAll[2].isVisible) {
                            buttonAll[2].startAnimation(animation)
                            backAll[2].visibility = View.VISIBLE
                        }
                        this.dataPlayer = "Kertas"
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
    //Animasi Random Pada Suitnya
    private fun animationRandLoop() {
        mutableListOf(backAll[3], backAll[4], backAll[5])
                .forEachIndexed { _, i ->
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (i.isVisible) {
                            i.visibility = View.INVISIBLE
                            Log.i("MainGame", "Hilang Sejenak Background nya Computer")
                        }
                    }, randDuration)
                }
        Handler(Looper.getMainLooper()).postDelayed({
            if (randNum <= 10) {
                controller.compRand()
                Log.i("MainActivity", "Perulangan Computer #${randNum}")
            } else {
                dataModel()
                randNum = 0
            }
        }, randDuration)
    }
    //Pemrosesan Data
    private fun dataModel() {

        val dataMauPlayer = ModelNyaComp(dataPlayer)
        Log.i("MainActivity", "Proses Suit Computer vs Pemain")
        controller.setDataPlayer(dataMauPlayer)
        controller.compProcess()
        controller.compareData()
    }
    //Button di lock ketika di proses
    private fun lockButton() {
        buttonAll[0].isEnabled = false
        buttonAll[1].isEnabled = false
        buttonAll[2].isEnabled = false
        Log.i("MainGame", "di Lock yekkk ga bisa main kapok kon")
    }
    //Reset semua
    private fun reset() {
        mutableListOf(
                backAll[0], backAll[1], backAll[2],
                backAll[3], backAll[4], backAll[5],
        ).forEachIndexed { _, i ->
            if (i.isVisible) {
                i.visibility = View.INVISIBLE
                Log.i("MainGame", "Background nya ilang semua")
                setImage.setImageResource(R.drawable.ic_battle_image)
            }
        }
        mutableListOf(resultImg[1], resultImg[2], resultImg[3])
                .forEachIndexed { _, i ->
                    i.animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).rotation(-180f).setDuration(0)
                            .start()
                }
        resultImg[0].animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(1000).start()

        dataPlayer = ""
        unlockButton()
        buttonAll[3].animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).rotation(180f).setDuration(1000)
                .start()
    }
    // Setelah reset di unlock
    private fun unlockButton() {
        if (!buttonAll[0].isEnabled) {
            buttonAll[0].isEnabled = true
            Log.i("MainGame", "Cieee bisa buka batu")
            if (!buttonAll[1].isEnabled) {
                buttonAll[1].isEnabled = true
                Log.i("MainGame", "Cieee bisa buka gunting")
                if (!buttonAll[2].isEnabled) {
                    buttonAll[2].isEnabled = true
                    Log.i("MainGame", "Cieee bisa buka kertas")
                }
            }

        }
    }
    //Animasi Random dari Computer
    override fun randAnim(animResult: String) {
        when (animResult) {
            "batu" -> {
                backAll[3].visibility = View.VISIBLE
                Log.i("MainGame", "Waw ada batu")
            }
            "gunting" -> {
                backAll[4].visibility = View.VISIBLE
                Log.i("MainGame", "Hmm gunting")
            }
            "kertas" -> {
                backAll[5].visibility = View.VISIBLE
                Log.i("MainGame", "Whoa ada kertas")
            }
        }
        randNum++
        animationRandLoop()
    }
    //Hasil dari Randomnya computer
    override fun resultRandom(resultRand: String) {
        when (resultRand) {
            "batu" -> backAll[3].visibility = View.VISIBLE
            "gunting" -> backAll[4].visibility = View.VISIBLE
            "kertas" -> backAll[5].visibility = View.VISIBLE
        }
    }
    //Menampilkan hasil dari kalah atau menang
    override fun result(resultNya: String) {
        resultImg[0].animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).setDuration(0).start()
        when (resultNya) {
            "PlayerMenang" -> {
                resultImg[1].visibility = View.VISIBLE
                resultImg[1].animate().alpha(1f).scaleX(-1f).scaleY(-1f).setDuration(1000).start()
            }
            "CompMenang" -> {
                resultImg[2].visibility = View.VISIBLE
                resultImg[2].animate().alpha(1f).scaleX(-1f).scaleY(-1f).setDuration(1000).start()
            }
            "Seri" -> {
                resultImg[3].visibility = View.VISIBLE
                resultImg[3].animate().alpha(1f).scaleX(-1f).scaleY(-1f).setDuration(1000).start()
            }
        }
        buttonAll[3].animate().alpha(1f).scaleX(-1f).scaleY(-1f).rotation(-180f).setDuration(1000).start()
        Log.i("MainActivity", "pemenangnya : $resultNya")
    }
}