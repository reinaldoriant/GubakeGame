package com.example.kertasguntingbatu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kertasguntingbatu.slidePage.SlidePage

private var animDuration = 1000L

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreen, SlidePage::class.java)
            startActivity(intent)
        }, 3000)
        val placeLogo = findViewById<ImageView>(R.id.logoImage)
        val imgLogo = "https://i.ibb.co/HC5ZPgD/splash-screen1.png"
        Glide.with(this)
                .load(imgLogo).error(R.drawable.ic_logo_image)
                .into(placeLogo)
    }
}