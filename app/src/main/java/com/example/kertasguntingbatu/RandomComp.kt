package com.example.kertasguntingbatu

import kotlin.random.Random

class RandomComp {
    fun randComp(): String {
        var randomResult = " "
        when (Random.nextInt(1, 3)) {
            1 -> randomResult = "Batu"
            2 -> randomResult = "Kertas"
            3 -> randomResult = "Gunting"
        }
        return randomResult
    }

}