package com.example.kertasguntingbatu.slidePage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SlidePageAdapter(Ad: FragmentActivity, listener: (CharSequence) -> Unit) :
    FragmentStateAdapter(Ad) {
    private val dataFragment = mutableListOf(
        FragmentSlide.newInstance("1", listener),
        FragmentSlide.newInstance("2",listener),
        FragmentSlide.newInstance("3", listener)

    )

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return dataFragment[position]
    }
}