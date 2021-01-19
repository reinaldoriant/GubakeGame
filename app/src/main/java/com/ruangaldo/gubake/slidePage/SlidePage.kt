package com.gubake.gubakegames.slidePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.gubake.gubakegames.MainMenu
import com.gubake.gubakegames.R
import kotlinx.android.synthetic.main.activity_slide_page.*

class SlidePage : AppCompatActivity() {
    private var name = ""
    private lateinit var buttonHide: ViewPager2.OnPageChangeCallback
    private val animation by lazy{
        AnimationUtils.loadAnimation(this, R.anim.bounce)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_slide_page)
        val pagerAdapter = SlidePageAdapter(this) { name = it.toString() }
        slideVP.adapter = pagerAdapter
        dots_indicator.setViewPager2(slideVP)
        buttonHide = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    btnNameInput.visibility = View.VISIBLE
                } else {
                    btnNameInput.visibility = View.GONE
                }
            }
        }
        slideVP.registerOnPageChangeCallback(buttonHide)

        btnNameInput.setOnClickListener {
            btnNameInput.startAnimation(animation)
            when {
                name != "" -> {
                    Intent(this, MainMenu::class.java)
                            .apply {
                                putExtra("dataName", name)
                                startActivity(this)
                            }
                }
                else -> {
                    Toast.makeText(this, "Silahkan isi namanya terlebih dahulu!", Toast.LENGTH_SHORT).show()
                }
            }
            Log.i("SlidePage", "klik button untuk input nama")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        slideVP.registerOnPageChangeCallback(buttonHide)
        Log.i("SlidePage", "Destroy button hide")
    }

    override fun onBackPressed() {
        finishAffinity()
        Log.i("SlidePage", "Keluar")
    }
}