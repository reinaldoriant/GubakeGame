package com.gubake.ui.splashScreen

import com.gubake.data.local.SharedPref

class SplashScreenPresenterImp(private val view: SplashScreenView): SplashScreenPresenter{
    override fun checkIsLogin() {
        if(SharedPref.isLogin == true)
            view.onLogged()
        else
            view.unLogged()
    }
}