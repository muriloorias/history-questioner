package com.muriloorias.historyquestioner.view.formregister

import android.graphics.Color
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.muriloorias.historyquestioner.R
import com.muriloorias.historyquestioner.view.Home.Home
import com.muriloorias.historyquestioner.view.formlogin.Login

private lateinit var auth: FirebaseAuth

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        val pickemail: EditText = findViewById(R.id.edEmail)
        val pickpassword: EditText = findViewById(R.id.password)
        val btnProced: Button = findViewById(R.id.btnRegister)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnProced.setOnClickListener {
            val email = pickemail.text.toString()
            val password = pickpassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                val snackbar = Snackbar.make(it, "Preencha todos os campos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val snackbar = Snackbar.make(
                                it,
                                "Usuário registrado com sucesso",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.setBackgroundTint(Color.GREEN)
                            snackbar.show()
                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val snackbar = Snackbar.make(
                                it,
                                "Falha no registro: ${task.exception?.message}",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.setBackgroundTint(Color.RED)
                            snackbar.show()
                        }
                    }
            }
        }
        btnLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
    }
}