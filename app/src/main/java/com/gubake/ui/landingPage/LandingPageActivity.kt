package com.gubake.ui.landingPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import com.gubake.R
import com.gubake.ui.login.LoginActivity

class LandingPageActivity : AppCompatActivity() {
    private var name: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)
        val viewpager2 by lazy {this.findViewById<ViewPager2>(R.id.viewpager2)}
        val dotsIndicator by lazy {this.findViewById<DotsIndicator>(R.id.dots_indicator)}
        val btnNext by lazy { this.findViewById<Button>(R.id.btnNext) }
        val viewPagerAdapter = ViewPagerAdapter(this) {
            name = it.toString()
        }
        viewpager2.adapter = viewPagerAdapter

        dotsIndicator.setViewPager2(viewpager2)

        btnNext.setOnClickListener {
            if(viewpager2.currentItem < 1){
                viewpager2.currentItem = viewpager2.currentItem.plus(1)
            }
            else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}