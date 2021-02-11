package com.gubake.ui.loginPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gubake.R
import com.gubake.data.remote.ApiModule
import com.gubake.databinding.ActivityLoginBinding
import com.gubake.ui.mainMenu.MainMenuAct
import com.gubake.ui.signUp.SignUpActivity
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum

class LoginAct : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        val factory = LoginFactory(ApiModule.service)
        this.viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.btSignIn.setOnClickListener {
            binding.btSignIn.text=("Loading...")
            binding.btSignIn.isEnabled=false
            viewModel.login()
        }
        binding.btSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        viewModel.resultLogin().observe(this, {bool->
            if (!bool) {
                startActivity(Intent(this, MainMenuAct::class.java))
                finish()
                binding.btSignIn.isEnabled=bool
            } else {
                binding.btSignIn.isEnabled=bool
                viewModel.buttonResult().observe(this,{but->
                    binding.btSignIn.text=but
                })
                viewModel.errorMsg().observe(this, { errMsg ->
                    viewModel.typeError().observe(this, { typeError ->
                        if(typeError=="email")
                        {
                            binding.etEmail.error = errMsg
                            binding.etPassword.error = null
                        }
                        else if (typeError=="password"){
                            binding.etPassword.error = errMsg
                            binding.etEmail.error = null
                        }
                    })
                })


            }
        })

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
        finish()
    }

}
