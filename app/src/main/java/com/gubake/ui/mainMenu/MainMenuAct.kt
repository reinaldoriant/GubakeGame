package com.gubake.ui.mainMenu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.gubake.R
import com.gubake.data.remote.ApiModule
import com.gubake.databinding.ActivityMainMenuBinding
import com.gubake.ui.about.InstructionActivity
import com.gubake.ui.gamehistory.GameHistoryAct
import com.gubake.ui.profileplayer.ProfilePlayer
import com.gubake.ui.profileteman.ProfileTeman
import com.gubake.utils.*
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum


class MainMenuAct : AppCompatActivity() {
    private lateinit var viewModel: MainMenuViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainMenuBinding>(
            this,
            R.layout.activity_main_menu
        )

        refreshToken()

        startService(Intent(this, GameMusic::class.java))
        val factory = MainMenuFactory(ApiModule.service)
        this.viewModel = ViewModelProvider(this, factory)[MainMenuViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.getUser()
        viewModel.imageResult().observe(this, {
            if (!it.isNullOrEmpty()) {
                Glide.with(this)
                    .load(it.toString())
                    .into(binding.IvPhotoUserProfile)
            }

        })
        viewModel.username().observe(this, {
            binding.tvNamaPanjangProfile.text = it.toString()
        })
        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, ChooseGamePlayAct::class.java))
            finish()
        }
        binding.btnMyFriends.setOnClickListener {
            startActivity(Intent(this, ProfileTeman::class.java))
            finish()
        }
        binding.btnGameHistory.setOnClickListener {
            startActivity(Intent(this, GameHistoryAct::class.java))
            finish()
        }
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, ProfilePlayer::class.java))
            finish()
        }
        binding.tvSeeInstruction.setOnClickListener {
            startActivity(Intent(this, InstructionActivity::class.java))
            stopMusic()
            finish()
        }
        binding.btnAnimInfo.setOnClickListener {
            startActivity(Intent(this, InstructionActivity::class.java))
            stopMusic()
            finish()
        }
        binding.btnOut.setOnClickListener {
            stopMusic()
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

    override fun onBackPressed() {
        super.onBackPressed()
        stopMusic()
        finish()
    }
    private fun stopMusic() {
        stopService(
            Intent(
                this,
                GameMusic::class.java
            )
        )
    }
}