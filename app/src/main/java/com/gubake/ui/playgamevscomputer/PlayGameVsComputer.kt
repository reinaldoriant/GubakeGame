package com.gubake.ui.playgamevscomputer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.gubake.R
import com.gubake.data.local.SharedPref
import com.gubake.data.remote.ApiModule
import com.gubake.ui.mainMenu.ChooseGamePlayAct
import com.gubake.utils.GamePlayMusic
import com.gubake.utils.SoundPlayer
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum

class PlayGameVsComputer : AppCompatActivity() {

    private val tag = "PlayGameVsCPU"
    private lateinit var playGameVsComputerViewModel: PlayGameVsComputerViewModel
    private var enemyNya: ImageButton? = null
    private val backgroundAll by lazy {
        mutableListOf(
            findViewById<FrameLayout>(R.id.backgroundBatu), findViewById(R.id.backgroundScissors),
            findViewById(R.id.backgroundPaper), findViewById(R.id.backgroundBatuComp),
            findViewById(R.id.backgroundScissorsComp), findViewById(R.id.backgroundPaperComp),
        )
    }
    private val buttonAll by lazy {
        mutableListOf(
            findViewById(R.id.batuPlayer),
            findViewById(R.id.scissorsPlayer),
            findViewById(R.id.paperPlayer),
            findViewById(R.id.batuComp),
            findViewById(R.id.scissorsComp),
            findViewById(R.id.paperComp),
            findViewById<ImageButton>(R.id.but_home)
        )
    }
    private lateinit var namaPlayer :String
    private var dataPlayer1 = ""
    private val randDuration = 1000L
    private val animRand = 200L
    private var randNum = 0

    //===== score counter ====
    private lateinit var tvScorePlayer: TextView
    private lateinit var tvScoreComputer: TextView
    private var scorePlayer = 0
    private var scoreComputer = 0

    private lateinit var sound: SoundPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkMonitoring()

        setContentView(R.layout.activity_play_game_vs_computer)
        startService(Intent(this, GamePlayMusic::class.java))
        val pref = SharedPref
        val factory = PlayGameVsComputerViewModel.Factory(ApiModule.service, pref)
        playGameVsComputerViewModel =
            ViewModelProvider(this, factory)[PlayGameVsComputerViewModel::class.java]

        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce)

        namaPlayer = pref.username.toString()
        findViewById<TextView>(R.id.player1).text = namaPlayer
        findViewById<TextView>(R.id.player2).text = resources.getText(R.string.computer)
        tvScorePlayer = findViewById(R.id.tvScorePlayer)
        tvScoreComputer = findViewById(R.id.tvScoreComputer)

        reset()
        val butNya = mutableListOf(
            buttonAll[0], buttonAll[1], buttonAll[2],
        )
        butNya.forEachIndexed { _, imageButton ->
            imageButton.setOnClickListener {
                when (it) {
                    buttonAll[0] -> {
                        if (backgroundAll[0].visibility != View.VISIBLE) {
                            buttonAll[0].startAnimation(animation)
                            backgroundAll[0].visibility = View.VISIBLE

                        }
                        dataPlayer1 = "batu"
                    }
                    buttonAll[1] -> {
                        if (backgroundAll[1].visibility != View.VISIBLE) {
                            buttonAll[1].startAnimation(animation)
                            backgroundAll[1].visibility = View.VISIBLE
                        }
                        dataPlayer1 = "gunting"
                    }
                    else -> {
                        if (backgroundAll[2].visibility != View.VISIBLE) {
                            buttonAll[2].startAnimation(animation)
                            backgroundAll[2].visibility = View.VISIBLE
                        }
                        dataPlayer1 = "kertas"
                    }
                }
                Toast.makeText(this, "${pref.username} Memilih $dataPlayer1", Toast.LENGTH_SHORT)
                    .show()
                Log.i("MainGameComputer", "${pref.username} memilih $dataPlayer1")
                lockButton()
                Handler(Looper.getMainLooper()).postDelayed({
                    playGameVsComputerViewModel.animAcakLoop()
                    Log.i("MainGameComputer", "Perulangan Computer #${randNum}")
                }, animRand)
                resetBackgroundAnimationRand()
                findViewById<TextView>(R.id.imageBattle).animate().alpha(0f).scaleX(0.5f)
                    .scaleY(0.5f).setDuration(500).start()
                buttonAll[6].animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).setDuration(500).start()
            }
        }
        buttonAll[6].setOnClickListener {
            buttonAll[6].startAnimation(animation)
            onBackPressed()
            Log.i("MainGameComputer", "playernya klik keluar")
        }

        sound = SoundPlayer(this)

        sound.playGameSound()


        playGameVsComputerViewModel.resultAnim.observe(this) {
            randAnim(it)
            sound.playClickSound()
        }
        playGameVsComputerViewModel.resultEnd.observe(this) {
            resultEnemy(it)
            sound.playClickSound()
        }
        playGameVsComputerViewModel.resultNya.observe(this) {
            popWinner(it.toString())
        }
    }

    private fun networkMonitoring(){
        //NetworkMonitor
        NoInternetDialogPendulum.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        // ...
                    }
                }

                cancelable = false // Optional
                noInternetConnectionTitle = "No Internet" // Optional
                noInternetConnectionMessage =
                    "Check your Internet connection and try again." // Optional
                showInternetOnButtons = true // Optional
                pleaseTurnOnText = "Please turn on" // Optional
                wifiOnButtonText = "Wifi" // Optional
                mobileDataOnButtonText = "Mobile data" // Optional

                onAirplaneModeTitle = "No Internet" // Optional
                onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
                pleaseTurnOffText = "Please turn off" // Optional
                airplaneModeOffButtonText = "Airplane mode" // Optional
                showAirplaneModeOffButtons = true // Optional
            }
        }.build()
    }

    //Button di lock ketika di proses
    private fun lockButton() {
        buttonAll.forEachIndexed { _, i ->
            i.isEnabled = false
        }
        Log.i("MainGameComputer", "Maaf tidak bisa diklik sedang proses bermain")
    }

    //unlock button semua
    private fun unlockButton() {
        if (!buttonAll[0].isEnabled && !buttonAll[1].isEnabled && !buttonAll[2].isEnabled && !buttonAll[6].isEnabled ) {
            buttonAll[0].isEnabled = true
            Log.i("MainGameComputer", "Anda memilih batu")
            buttonAll[1].isEnabled = true
            Log.i("MainGameComputer", "Anda memilih gunting")
            buttonAll[2].isEnabled = true
            Log.i("MainGameComputer", "Anda memilih kertas")
            buttonAll[6].isEnabled = true
            Log.i("MainGameComputer", "Anda memilih kertas")
        }
    }

    //Reset semua
    private fun reset() {
        backgroundAll.forEachIndexed { _, i ->
            if (i.visibility == View.VISIBLE) {
                i.visibility = View.INVISIBLE
                Log.i("MainGameComputer", "Background tidak aktif")
            }
        }
        findViewById<TextView>(R.id.imageBattle).animate().alpha(1f).scaleX(1f).scaleY(1f)
            .setDuration(randDuration).start()
        buttonAll[6].animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(randDuration).start()
        dataPlayer1 = ""
        unlockButton()
    }

    //Animasi Random Pada Suitnya
    private fun resetBackgroundAnimationRand() {
        mutableListOf(backgroundAll[3], backgroundAll[4], backgroundAll[5])
            .forEachIndexed { _, i ->
                Handler(Looper.getMainLooper()).postDelayed({
                    if (i.visibility == View.VISIBLE) {
                        i.visibility = View.INVISIBLE
                        Log.i("MainGameComputer", "Hilang Sejenak Background nya Computer")
                    }
                }, animRand)
            }
    }

    //Animasi Random dari Computer
    private fun randAnim(animResult: String) {
        when (animResult) {
            "batu" -> {
                backgroundAll[3].visibility = View.VISIBLE
                Log.i("MainGameComputer", "CPU memilih batu")
            }
            "gunting" -> {
                backgroundAll[4].visibility = View.VISIBLE
                Log.i("MainGameComputer", "CPU memilih gunting")
            }
            "kertas" -> {
                backgroundAll[5].visibility = View.VISIBLE
                Log.i("MainGameComputer", "CPU memilih kertas")
            }
        }
        resetBackgroundAnimationRand()
    }

    //Hasil dari Randomnya computer
    private fun resultEnemy(resultEnemy: String) {
        when (resultEnemy) {
            "batu" -> {
                enemyNya = buttonAll[3]
                backgroundAll[3].visibility = View.VISIBLE
            }
            "gunting" -> {
                enemyNya = buttonAll[4]
                backgroundAll[4].visibility = View.VISIBLE
            }
            "kertas" -> {
                enemyNya = buttonAll[5]
                backgroundAll[5].visibility = View.VISIBLE
            }
        }
        Toast.makeText(this, "CPU Memilih $resultEnemy", Toast.LENGTH_SHORT).show()
        playGameVsComputerViewModel.compareData(dataPlayer1, resultEnemy)
    }

    private fun popWinner(resultNya: String) {
        stopService(Intent(this, GamePlayMusic::class.java))
        var winner = ""
        when (resultNya) {
            "Player Win" -> {
                winner = "$namaPlayer\nMENANG!"
                scorePlayer++
                tvScorePlayer.text = scorePlayer.toString()

            }
            "Opponent Win" -> {
                winner = "Computer\nMENANG!"
                scoreComputer++
                tvScoreComputer.text = scoreComputer.toString()
            }
            "Draw" -> {
                winner = "SERI!"
            }
        }
        Log.e(tag, "Hasil: $winner")
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val view =
                    LayoutInflater.from(this).inflate(R.layout.result_game_dialog, null, false)
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setView(view)
                val dialogD1 = dialogBuilder.create()
                dialogD1.setCancelable(false)
                val animation by lazy { view.findViewById<LottieAnimationView>(R.id.lav_success) }
                val winnerInfo by lazy { view.findViewById<TextView>(R.id.winner) }
                val playAgain by lazy { view.findViewById<Button>(R.id.play_again) }
                val backMenu by lazy { view.findViewById<Button>(R.id.back_menu) }
                winnerInfo.text = winner
                sound.playGameSound()

                //View animation
                animation.speed = 0.5F
                animation.repeatCount = 5
                animation.repeatMode = LottieDrawable.RESTART
                animation.playAnimation()

                playAgain.setOnClickListener {
                    reset()
                    dialogD1.dismiss()
                    startService(Intent(this, GamePlayMusic::class.java))
                }
                backMenu.setOnClickListener {
                    dialogD1.dismiss()
                    val intent = Intent(this, ChooseGamePlayAct::class.java)
                    startActivity(intent)
                    stopService(Intent(this, GamePlayMusic::class.java))
                    finish()

                }
                if (!isFinishing) {
                    dialogD1.show()
                }
            }, randDuration
        )
    }

    override fun onBackPressed() {
        startActivity(Intent(this, ChooseGamePlayAct::class.java))
        stopService(Intent(this, GamePlayMusic::class.java))
        finish()
    }

}

