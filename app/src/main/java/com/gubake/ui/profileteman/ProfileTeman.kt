package com.gubake.ui.profileteman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.gubake.R
import com.gubake.ui.menu.MainMenuActivity
import kotlinx.android.synthetic.main.activity_profile_teman.*
import kotlinx.android.synthetic.main.addfriend_dialog.view.*


class ProfileTeman : AppCompatActivity(), TemanView {
    private lateinit var presenter: TemanPresenter
    private var usernamePlayer = mutableListOf<String>()
    private var usernameEmail = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_teman)
        presenter = TemanPresenterImp(this)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        presenter.playerName()
        fetchData()
        btadd.setOnClickListener {
            val view = LayoutInflater.from(this).inflate(R.layout.addfriend_dialog, null, false)
            val dialogBuilder = AlertDialog.Builder(this).setView(view)
            val dialogD1 = dialogBuilder.create()
            dialogD1.setCancelable(false)
            view.btSaveFriend.setOnClickListener {
                val namaTeman = view.etNama.text.toString()
                val emailTeman = view.etEmail.text.toString().trim()
                if (namaTeman.isEmpty() && emailTeman.isEmpty()) {
                    view.etNama.error = "Username harus diisi"
                    view.etEmail.error = "Email harus diisi"
                } else if (namaTeman.isEmpty()) {
                    view.etNama.error = "Username harus diisi"
                } else if (emailTeman.isEmpty()) {
                    view.etEmail.error = "Email harus diisi"
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailTeman).matches()) {
                    view.etEmail.error = "Mohon isi email yang benar"
                } else if(usernamePlayer[0]==namaTeman&&usernameEmail[0]==emailTeman){
                    view.etNama.error = "Nama teman tidak boleh sama dengan player"
                    view.etEmail.error = "Email teman tidak boleh sama dengan player"
                }else {
                    presenter.addTeman(namaTeman, emailTeman)
                    dialogD1.dismiss()

                }
            }
            view.btClose.setOnClickListener {
                dialogD1.dismiss()
            }
            dialogD1.show()
        }
        ib_home.setOnClickListener {
            startActivity(Intent(this, MainMenuActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData() {
        presenter.listTeman(recyclerView, this@ProfileTeman)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroyDB()
    }

    override fun onSuccessTeman(msg:String) {
        fetchData()
        Toast.makeText(
            this@ProfileTeman, msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onFailedTeman(msg: String) {
        Toast.makeText(
            this@ProfileTeman, msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun nameEmail(username: String, email: String) {
        usernamePlayer.add(username)
        usernameEmail.add(email)
        tvNamePlayer.text=username
        tvEmailPlayer.text=email
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainMenuActivity::class.java))
        finish()
    }



}