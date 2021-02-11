package com.gubake.ui.pilihlawan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gubake.R
import com.gubake.data.database.TemanDatabase
import com.gubake.data.local.SharedPref
import com.gubake.ui.mainMenu.MainMenuAct
import com.gubake.utils.GamePlayMusic
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum

class PilihLawan : AppCompatActivity() {
    private val tag : String = "PilihLawan"
    private lateinit var pilihLawanViewModel: PilihLawanViewModel
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_lawan)
        startService(Intent(this, GamePlayMusic::class.java))
        val pref = SharedPref
        val mDB: TemanDatabase = TemanDatabase.getInstance(this)!!

        val factory = PilihLawanViewModel.Factory(mDB, pref)
        pilihLawanViewModel = ViewModelProvider(this, factory)[PilihLawanViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerView)
        val ivBack : ImageView = findViewById(R.id.ivBackPilih)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        ivBack.setOnClickListener {
            startActivity(Intent(this, MainMenuAct::class.java))
            stopMusic()
            finish()
        }

        fetchData()

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
        Log.e(tag,"fetchData")
        pilihLawanViewModel.showList(recyclerView, this@PilihLawan)
    }

    override fun onDestroy() {
        super.onDestroy()
        pilihLawanViewModel.destroyDB()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainMenuAct::class.java))
        stopMusic()
        finish()
    }

    private fun stopMusic() {
        stopService(Intent(this, GamePlayMusic::class.java))
    }
}