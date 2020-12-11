package com.example.kertasguntingbatu.slidePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.kertasguntingbatu.MainMenu
import com.example.kertasguntingbatu.R
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.activity_slide_page.*

class SlidePage : AppCompatActivity() {
    private var name = ""
    private lateinit var buttonHide: ViewPager2.OnPageChangeCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_slide_page)
        val pagerAdapter = SlidePageAdapter(this) { name = it.toString() }
        slideVP.adapter = pagerAdapter
        dots_indicator.setViewPager2(slideVP)
        buttonHide = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    imageButton.visibility = View.VISIBLE
                } else {
                    imageButton.visibility = View.GONE
                }
            }
        }
        slideVP.registerOnPageChangeCallback(buttonHide)

        imageButton.setOnClickListener {
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        slideVP.registerOnPageChangeCallback(buttonHide)
    }
}