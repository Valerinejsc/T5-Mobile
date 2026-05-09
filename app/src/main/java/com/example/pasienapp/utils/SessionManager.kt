package com.example.pasienapp.utils

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    companion object {
        const val KEY_TOKEN = "token"
        const val KEY_NAME  = "user_name"
    }

    fun saveSession(token: String, name: String) {
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .putString(KEY_NAME, name)
            .apply()
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun getUserName(): String? = prefs.getString(KEY_NAME, null)

    fun clearSession() = prefs.edit().clear().apply()
}