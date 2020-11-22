package com.example.kertasguntingbatu

import android.security.identity.ResultData

class ControllerNya(val listener: IControllerNya) {
    private var dataPlayer: ModelNya? = null
    private var dataComp: ModelNya? = null

    // private var data: ModelNya? = null
    fun setDataPlayer(dataPlayer: ModelNya) {
        this.dataPlayer = dataPlayer
    }

    fun setDataComp(dataComp: ModelNya) {
        this.dataComp = dataComp
    }

    /* fun getDataResult(data:ModelNya){
         this.data=data
     }*/
    fun compareData() {
        val dataComp: String? = null
        val dataPlayer: String? = null
        var dataResult: String? = null
        //dataPlayer  Menang
        if (dataPlayer.equals("Batu", true) && dataComp.equals("Gunting", true) ||
                dataPlayer.equals("Kertas", true) && dataComp.equals("Batu", true) ||
                dataPlayer.equals("Gunting", true) && dataComp.equals("Kertas", true)) {
            dataResult = "PlayerMenang"
            listener.result(dataResult)
        }
        //data comp Menang
        else if (dataComp.equals("Batu", true) && dataPlayer.equals("Gunting", true) ||
                dataComp.equals("Kertas", true) && dataPlayer.equals("Batu", true) ||
                dataComp.equals("Gunting", true) && dataPlayer.equals("Kertas", true)) {
            //dataResult = "CompMenang"
        } else {
            // dataResult = "Seri"
        }
    }


}