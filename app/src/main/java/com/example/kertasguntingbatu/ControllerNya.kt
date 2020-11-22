package com.example.kertasguntingbatu

class ControllerNya(private val listener: IControllerNya) {
    private var dataPlayer: ModelNya? = null
    private var dataComp: ModelnyaComp? = null

    // private var data: ModelNya? = null
    fun setDataPlayer(dataPlayer: ModelNya) {
        this.dataPlayer = dataPlayer
    }

    fun setDataComp(dataComp: ModelnyaComp) {
        this.dataComp = dataComp
    }

    /* fun getDataResult(data:ModelNya){
         this.data=data
     }*/
    fun compareData() {

        //dataPlayer  Menang
        if (dataPlayer!!.dataPlayer.equals("Batu", true) && dataComp!!.dataComp.equals("Gunting", true) ||
                dataPlayer!!.dataPlayer.equals("Kertas", true) && dataComp!!.dataComp.equals("Batu", true) ||
                dataPlayer!!.dataPlayer.equals("Gunting", true) && dataComp!!.dataComp.equals("Kertas", true)) {
            listener.result("Player Menang")
        }
        //data comp Menang
        else if (dataComp!!.dataComp.equals("Batu", true) && dataPlayer!!.dataPlayer.equals("Gunting", true) ||
                dataComp!!.dataComp.equals("Kertas", true) && dataPlayer!!.dataPlayer.equals("Batu", true) ||
                dataComp!!.dataComp.equals("Gunting", true) && dataPlayer!!.dataPlayer.equals("Kertas", true)) {
            listener.result("Player Kalah")
        } else {
            listener.result("Seri")
        }
    }


}