package com.example.kertasguntingbatu.playGame

import android.util.Log


class ControllerNya(private val listener: IControllerNya) {
    private var dataPlayer: ModelNya? = null
    private var dataCompRand = arrayListOf("batu", "gunting","kertas")
    private var compRandData = dataCompRand.random()

    fun setDataPlayer(dataPlayer: ModelNya) {
        this.dataPlayer = dataPlayer
        Log.i("ContollerPermainan", "game mode ${dataPlayer.modeGame} dari Model")
        Log.i("ContollerPermainan", "Ambil data Pilihan pemain 1 ${dataPlayer.dataPlayer1} dari Model")
        Log.i("ContollerPermainan", "Ambil data Pilihan pemain 2 ${dataPlayer.dataPlayer2} dari Model")
    }

    fun compRand() {
        val dataCompFix=dataCompRand.random()
        listener.randAnim(dataCompFix)
    }
    fun chooseEnemy(){
        if (dataPlayer!!.modeGame=="vsCPU"){
            listener.resultEnemy(compRandData)
            Log.i("ContollerPermainan", "Com memilih = $compRandData")
        }
        else{
            listener.resultEnemy(dataPlayer!!.dataPlayer2)
            Log.i("ControllerNya", "Pemain 2 memilih = ${dataPlayer!!.dataPlayer2}")
        }
    }
    fun compareData() {
        val dataPlay2: String= if(dataPlayer!!.modeGame=="vsCPU"){
            compRandData
        }else{
            dataPlayer!!.dataPlayer2
        }
        //dataPlayer  Menang
        val resultNya:String = if (dataPlayer!!.dataPlayer1=="batu" && dataPlay2=="gunting" ||
                dataPlayer!!.dataPlayer1=="kertas" && dataPlay2=="batu" ||
                dataPlayer!!.dataPlayer1=="gunting" && dataPlay2=="kertas") {
            "P1Win"
        }
        //data comp Menang
        else if (dataPlay2 == "batu" && dataPlayer!!.dataPlayer1=="gunting" ||
                dataPlay2=="kertas"&& dataPlayer!!.dataPlayer1=="batu" ||
                dataPlay2=="gunting" && dataPlayer!!.dataPlayer1=="kertas") {
            "P2Win"
        } else {
            "Seri"
        }
        listener.resultWinner(resultNya)
        Log.i("Controllernya", "Siapa nih yang menang $resultNya")
    }


}