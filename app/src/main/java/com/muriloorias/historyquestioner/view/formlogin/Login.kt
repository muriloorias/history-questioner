package com.muriloorias.historyquestioner.view.formlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.muriloorias.historyquestioner.R
import com.google.firebase.auth.FirebaseAuth
import com.muriloorias.historyquestioner.view.Home.Home
import com.muriloorias.historyquestioner.view.formregister.Register

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        val getemail: EditText = findViewById(R.id.edEmail)
        val getpassword: EditText = findViewById(R.id.password)
        val btnProced: Button = findViewById(R.id.btnContinue)
        val btnDontHaveAcount: Button = findViewById(R.id.btnDontHaveAcount)

        btnProced.setOnClickListener {
            val email = getemail.text.toString()
            val password = getpassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                val snackbar = Snackbar.make(
                    it,
                    "Preencha todos os campos",
                    Snackbar.LENGTH_SHORT
                )
            }else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){task ->
                        if (task.isSuccessful){
                            val use = auth.currentUser
                            val snackbar = Snackbar.make(
                                it,
                                "Usuário logado com sucesso",
                                Snackbar.LENGTH_SHORT
                            )
                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            val snackbar = Snackbar.make(
                                it,
                                "a autentificação falhou ${task.exception?.message}",
                                Snackbar.LENGTH_SHORT
                            )
                        }
                    }
            }
        }
        btnDontHaveAcount.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }
    }
}