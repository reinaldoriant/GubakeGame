package com.gubake.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.gubake.R
import com.gubake.ui.landingPage.LandingPageActivity
import com.gubake.ui.mainMenu.MainMenuAct

class SplashScreenActivity : AppCompatActivity(), SplashScreenNavigator {
    private lateinit var splashScreenViewModel: SplashScreenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val ivSplashScreen1 by lazy {this.findViewById<ImageView>(R.id.ivSplashScreen1)}
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash_screen)
        ivSplashScreen1.startAnimation(animation)
        splashScreenViewModel = ViewModelProvider(this, defaultViewModelProviderFactory)[SplashScreenViewModel::class.java]
        splashScreenViewModel.navigator = this
        splashScreenViewModel.checkIsLogin()
    }

    override fun onLogged() {
        Handler(Looper.getMainLooper()).postDelayed({

            startActivity(Intent(this, MainMenuAct::class.java))
            finish() }, 3000)
    }

    override fun unLogged() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LandingPageActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}