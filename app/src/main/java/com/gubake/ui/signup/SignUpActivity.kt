package com.gubake.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gubake.R
import com.gubake.ui.login.LoginActivity

class SignUpActivity : AppCompatActivity(), SignUpView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val btnSignUp by lazy { this.findViewById<Button>(R.id.btnSignUp) }
        val logo by lazy { this.findViewById<ImageView>(R.id.ivLogo) }
        val username by lazy {this.findViewById<EditText>(R.id.etUsername)}
        val password by lazy {this.findViewById<EditText>(R.id.etPassword)}
        val rePassword by lazy {this.findViewById<EditText>(R.id.etRePassword)}
        val email by lazy {this.findViewById<EditText>(R.id.etEmail)}
        val presenter = SignUpPresenterImp(this)
        val field = mutableListOf<EditText>(username, password, rePassword, email)
        val nameField = mutableListOf("Username", "Password", "Re-Password", "Email")
        var checker = true

        Glide.with(this)
            .load("https://i.ibb.co/HC5ZPgD/splash-screen1.png")
            .into(logo)
        btnSignUp.setOnClickListener {
            field.forEach{
                if (it.text.isEmpty()){
                    val btn = nameField[field.indexOf(it)]
                    it.error = "$btn harus diisi"
                    checker = false
                }
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                email.error = "Mohon isi email yang benar"
            }
            else {
                if(rePassword.text.toString() != password.text.toString()){
                    rePassword.error = "Password tidak sama"
                }
                else if (checker){
                    presenter.signUp(username.text.toString(), password.text.toString(), email.text.toString())
                }
            }
            checker = true
        }
    }

    override fun onSuccess() {
        Toast.makeText(this, "User berhasil didaftarkan", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}