package com.example.kertasguntingbatu

import android.util.Log



class ControllerNya(private val listener: IControllerNya) {
    private var dataPlayer: ModelNya? = null
    private var dataCompRand = arrayListOf("batu", "kertas", "gunting","kertas", "batu", "gunting","gunting", "batu", "kertas")
    private var compRandData = dataCompRand.random()

    fun setDataPlayer(dataPlayer: ModelNya) {
        this.dataPlayer = dataPlayer
    }

    fun compProcess() {
        listener.resultRandom(compRandData)
        Log.i("ControllerNya", "Akhirnya Comp memilih :$compRandData")
    }

    fun compRand() {
        val dataCompFix=dataCompRand.random()
        listener.randAnim(dataCompFix)
    }

    fun compareData() {
        //dataPlayer  Menang
        val resultNya:String = if (dataPlayer!!.dataPlayer==("Batu") && compRandData==("gunting") ||
                dataPlayer!!.dataPlayer==("Kertas") && compRandData==("batu") ||
                dataPlayer!!.dataPlayer==("Gunting") && compRandData==("kertas")) {
            "PlayerMenang"
        }
        //data comp Menang
        else if (compRandData == "batu" && dataPlayer!!.dataPlayer==("Gunting") ||
                compRandData==("kertas") && dataPlayer!!.dataPlayer==("Batu") ||
                compRandData==("gunting") && dataPlayer!!.dataPlayer==("Kertas")) {
            "CompMenang"
        } else {
            "Seri"
        }
        listener.result(resultNya)
        Log.i("Controllernya", "Siapa nih yang menang $resultNya")
    }


}