package com.example.kertasguntingbatu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreen, MainMenu::class.java)
            startActivity(intent)
        }, 3000)
        val placeLogo = findViewById<ImageView>(R.id.logoImage)
        val imgLogo = "https://i.ibb.co/HC5ZPgD/splash-screen1.png"
        Glide.with(this)
                .load(imgLogo).error(R.drawable.ic_logo_backup)
                .apply(RequestOptions()
                        .placeholder(R.drawable.ic_logo_backup).centerCrop()
                ).into(placeLogo)
    }
}