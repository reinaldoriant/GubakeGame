package com.gubake.ui.signUp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gubake.R
import com.gubake.data.remote.ApiModule
import com.gubake.databinding.ActivitySignUpBinding
import com.gubake.ui.loginPage.LoginAct
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum

class SignUpActivity : AppCompatActivity() {
    private lateinit var viewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = SignUpFactory(ApiModule.service)
        viewModel = ViewModelProvider(this, factory)[SignUpViewModel::class.java]
        val binding =
            DataBindingUtil.setContentView<ActivitySignUpBinding>(this, R.layout.activity_sign_up)
        binding.viewModel = viewModel
        binding.icBack.setOnClickListener {
            startActivity(Intent(this, LoginAct::class.java))
            finish()
        }
        binding.btnSignUp.setOnClickListener {
            binding.btnSignUp.text = ("Loading...")
            binding.btnSignUp.isEnabled = false
            viewModel.signUp()
        }
        viewModel.resultLogin().observe(this, { bool ->
            if (!bool) {
                finish()
                startActivity(Intent(this, NotifSignUpActivity::class.java))
                binding.btnSignUp.isEnabled = bool
            } else {
                binding.btnSignUp.isEnabled = bool
                viewModel.buttonResult().observe(this, { but ->
                    binding.btnSignUp.text = but
                    viewModel.errorMsg().observe(this, { errMsg ->
                        viewModel.typeError().observe(this, { typeErr ->
                            when (typeErr) {
                                "email" -> {
                                    binding.etEmail.error = errMsg
                                    binding.etUsername.error = null
                                    binding.etPassword.error = null
                                }
                                "username" -> {
                                    binding.etUsername.error = errMsg
                                    binding.etEmail.error = null
                                    binding.etPassword.error = null
                                }
                                "password" -> {
                                    binding.etPassword.error = errMsg
                                    binding.etUsername.error = null
                                    binding.etEmail.error = null
                                }
                                "repassword" -> {
                                    binding.etRePassword.error = errMsg
                                }
                            }
                        })

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
        super.onBackPressed()
        startActivity(Intent(this, LoginAct::class.java))
        finish()
    }
}