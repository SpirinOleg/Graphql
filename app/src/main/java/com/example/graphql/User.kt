package com.example.graphql

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object User {
    private const val KEY_TOKEN = "TOKEN"
    var tokenUser = ""
    private fun preferences(context: Context): SharedPreferences {

        val masterKeyAlias = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

//    fun getToken(context: Context): String? {
//        return preferences(context).getString(KEY_TOKEN, null)
//    }

    fun setToken(context: Context, token: String) {
        tokenUser = token
//        preferences(context).edit().apply {
//            putString(KEY_TOKEN, token)
//            apply()
//        }
    }

    fun removeToken(context: Context) {
        preferences(context).edit().apply {
            remove(KEY_TOKEN)
            apply()
        }
    }
}