package com.gubake.ui.splashScreen

import androidx.lifecycle.ViewModel
import com.gubake.data.local.SharedPref

class SplashScreenViewModel : ViewModel() {
    var  navigator: SplashScreenNavigator? = null

    fun checkIsLogin() {
        if(SharedPref.isLogin == true)
            navigator?.onLogged()
        else
            navigator?.unLogged()
    }
}