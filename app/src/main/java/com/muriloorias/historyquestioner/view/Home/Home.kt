package com.muriloorias.historyquestioner.view.Home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.muriloorias.historyquestioner.R
import com.muriloorias.historyquestioner.view.Questions.QustionsFirstRevolutions
import com.muriloorias.historyquestioner.view.formlogin.Login

class Home : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val userEmailTextView: TextView = findViewById(R.id.userEmail)
        val btnLogout: Button = findViewById(R.id.btnLogout)
        val btnReadFirstTheme: Button = findViewById(R.id.btnReadFirstTheme)
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            userEmailTextView.text = "${currentUser.email}"
        }
        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        val webView : WebView = findViewById(R.id.textAboutThemeOne)
        val link = "https://pt.wikipedia.org/wiki/Revolu%C3%A7%C3%A3o_Industrial"
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        btnReadFirstTheme.setOnClickListener{
            webView.visibility = View.VISIBLE
            webView.loadUrl(link)
        }
        val answerTheQuestions : Button = findViewById(R.id.answerTheQuestions)
        answerTheQuestions.setOnClickListener{
            val intent = Intent(this, QustionsFirstRevolutions::class.java)
            startActivity(intent)
            finish()
        }
    }
}