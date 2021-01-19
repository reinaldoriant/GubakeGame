package com.gubake.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gubake.R
import com.gubake.ui.menu.MainMenuActivity
import com.gubake.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity(), LoginView {
    private var presenter: LoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val logo by lazy {this.findViewById<ImageView>(R.id.logo)}
        val username by lazy {this.findViewById<EditText>(R.id.etUsername)}
        val password by lazy {this.findViewById<EditText>(R.id.etPassword)}
        val btnLogin by lazy { this.findViewById<Button>(R.id.btnLogin) }
        val btnSignUp by lazy { this.findViewById<Button>(R.id.btnSignUp) }
        presenter = LoginPresenterImp(this)
        Glide.with(this)
            .load("https://i.ibb.co/HC5ZPgD/splash-screen1.png")
            .into(logo)

        btnLogin.setOnClickListener {
            if(username.text.toString().isEmpty()){
                username.error = "Username harus diisi"
            }
            if(password.text.toString().isEmpty()){
                password.error = "Password harus diisi"
            }
            else{
                presenter?.login(username.text.toString(), password.text.toString())
            }
        }

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }

    override fun onSuccess() {
        finish()
        startActivity(Intent(this, MainMenuActivity::class.java))
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        finish()
    }
}