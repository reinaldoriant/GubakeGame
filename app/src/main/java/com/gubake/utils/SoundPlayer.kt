@file:Suppress("DEPRECATION")

package com.gubake.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.gubake.R

class SoundPlayer(context: Context?) {
    private var audioAttributes: AudioAttributes? = null
    private val maxStreams = 5

    companion object {
        private lateinit var soundPool: SoundPool
        private var winSound: Int = 0
        private var clickSound: Int = 0
    }

    init {

        //SoundPool is deprecated in API level 21 (lollipop)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            soundPool = SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(maxStreams)
                .build()
        } else {
            soundPool = SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0)
        }
        winSound = soundPool.load(context, R.raw.success, 1)
        clickSound = soundPool.load(context, R.raw.click, 1)
    }

    fun playGameSound() {
        soundPool.play(winSound, 1.0f, 1.0f, 1, 0, 1.0f)
    }

    fun playClickSound() {
        soundPool.play(clickSound, 1.0f, 1.0f, 1, 0, 1.0f)
    }
}