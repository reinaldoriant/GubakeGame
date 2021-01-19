package com.gubake.ui.signup

import com.gubake.utils.App.Companion.context
import com.gubake.utils.App.Companion.mDB
import com.gubake.data.database.Pemain
import com.gubake.data.database.TemanDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpPresenterImp(private val view: SignUpView): SignUpPresenter{
    override fun signUp(username: String, password: String, email: String) {
        mDB = context?.let { TemanDatabase.getInstance(it) }
        val pemainBaru = Pemain(null, username, password, email)
        GlobalScope.launch(Dispatchers.IO) {
            val checker = mDB?.pemainDao()?.getPemainByUsername(username)
            if(checker == null){
                mDB?.pemainDao()?.insertPemain(pemainBaru)
            }
            launch(Dispatchers.Main) {
                if (checker == null){
                    view.onSuccess()
                }
                else{
                    view.onError("Username telah digunakan")
                }
            }
        }
    }
}