package com.example.pasienapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pasienapp.R
import com.example.pasienapp.api.RetrofitClient
import com.example.pasienapp.model.LoginRequest
import com.example.pasienapp.utils.SessionManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        etEmail     = findViewById(R.id.etEmail)
        etPassword  = findViewById(R.id.etPassword)
        btnLogin    = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)
        tvError     = findViewById(R.id.tvError)

        btnLogin.setOnClickListener {
            val email    = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty()) {
                tvError.text = "Email tidak boleh kosong"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                tvError.text = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            tvError.text = ""
            doLogin(email, password)
        }
    }

    private fun doLogin(email: String, password: String) {
        setLoading(true)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.login(
                    LoginRequest(email, password)
                )

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success) {
                        val token = body.data?.token ?: ""
                        val name  = body.data?.user?.name ?: "User"

                        sessionManager.saveSession(token, name)

                        val intent = Intent(this@LoginActivity, PasienActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        tvError.text = body?.message ?: "Login gagal"
                    }
                } else {
                    tvError.text = "Login gagal: ${response.code()}"
                }

            } catch (e: Exception) {
                tvError.text = "Koneksi gagal: ${e.message}"
            } finally {
                setLoading(false)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        btnLogin.isEnabled     = !isLoading
    }
}