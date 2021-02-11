package com.gubake.ui.profileplayer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.gubake.R
import com.gubake.data.local.SharedPref
import com.gubake.ui.about.AboutActivity
import com.gubake.ui.loginPage.LoginAct
import com.gubake.ui.mainMenu.MainMenuAct
import com.gubake.utils.GameMusic
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum

class ProfilePlayer : AppCompatActivity() {
    private val tag: String = "ProfilePlayer"
    private lateinit var profilePlayerViewModel: ProfilePlayerViewModel
    private lateinit var lParent: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_player)

        val pref = SharedPref
        val factory = ProfilePlayerViewModel.Factory(pref)
        profilePlayerViewModel =
            ViewModelProvider(this, factory)[ProfilePlayerViewModel::class.java]

        lParent = findViewById(R.id.lParent)
        val btEdit = findViewById<Button>(R.id.btEdit)
        val ivBack = findViewById<ImageView>(R.id.ivBackProfile)
        val ivProfile = findViewById<ImageView>(R.id.ivProfile)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val signOut=findViewById<Button>(R.id.btSignOut)
        val about =findViewById<TextView>(R.id.tvAbout)
        val aboutLottie = findViewById<LottieAnimationView>(R.id.lottieAbout)

        fetchData()

        ivBack.setOnClickListener {
            finish()
            startActivity(Intent(this, MainMenuAct::class.java))
        }
        btEdit.setOnClickListener {
            val intent = Intent(this, EditProfilePlayer::class.java)
            startActivity(intent)
            finish()
        }
        profilePlayerViewModel.resultUser.observe(this) {
            tvName.text = it.data.username
            tvEmail.text = it.data.email
            btEdit.text = ("Edit Profile")
            Glide
                .with(this)
                .load(it.data.photo)
                .centerCrop()
                .circleCrop()
                .placeholder(R.drawable.ic_user)
                .into(ivProfile)
        }
        profilePlayerViewModel.resultMessage.observe(this) {
            Log.e(tag, it.toString())
            if (it == "Token is expired" || it == "Invalid Token") {
                val snackbar = Snackbar.make(
                    lParent,
                    "Waktu bermain sudah selesai, main lagi? silahkan Login",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar.setAction("Login") {
                    snackbar.dismiss()
                    startActivity(Intent(this, LoginAct::class.java))
                    finish()
                }.show()
            }
        }
        signOut.setOnClickListener {
            SharedPref.isLogin=false
            stopService(Intent(this,GameMusic::class.java))
            startActivity(Intent(this, LoginAct::class.java))
            finish()
        }
        about.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
            finish()
        }
        aboutLottie.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
            finish()
        }
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

    override fun onResume() {
        super.onResume()
        fetchData()
    }
    private fun fetchData() {
        profilePlayerViewModel.playerData()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainMenuAct::class.java))
        finish()
    }
}