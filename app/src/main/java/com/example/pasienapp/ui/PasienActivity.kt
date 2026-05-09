package com.example.pasienapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pasienapp.R
import com.example.pasienapp.adapter.PasienAdapter
import com.example.pasienapp.api.RetrofitClient
import com.example.pasienapp.utils.SessionManager
import kotlinx.coroutines.launch

class PasienActivity : AppCompatActivity() {

    private lateinit var rvPasien: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView
    private lateinit var tvNamaUser: TextView
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasien)

        sessionManager = SessionManager(this)

        rvPasien    = findViewById(R.id.rvPasien)
        progressBar = findViewById(R.id.progressBar)
        tvError     = findViewById(R.id.tvError)
        tvNamaUser  = findViewById(R.id.tvNamaUser)

        val nama = sessionManager.getUserName()
        tvNamaUser.text = "Halo, $nama 👋"

        rvPasien.layoutManager = LinearLayoutManager(this)

        fetchPasien()
    }

    private fun fetchPasien() {
        setLoading(true)

        val token = "Bearer ${sessionManager.getToken()}"

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getPasien(token)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success) {
                        rvPasien.adapter = PasienAdapter(body.data)
                    } else {
                        showError(body?.message ?: "Gagal memuat data")
                    }
                } else {
                    showError("Error: ${response.code()}")
                }

            } catch (e: Exception) {
                showError("Koneksi gagal: ${e.message}")
            } finally {
                setLoading(false)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        rvPasien.visibility    = if (isLoading) View.GONE    else View.VISIBLE
    }

    private fun showError(msg: String) {
        tvError.text        = msg
        tvError.visibility  = View.VISIBLE
        rvPasien.visibility = View.GONE
    }
}