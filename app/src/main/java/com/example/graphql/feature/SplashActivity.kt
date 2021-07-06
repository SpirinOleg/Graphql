package com.example.graphql.feature

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.graphql.BuildConfig
import com.example.graphql.MainActivity
import com.example.graphql.R
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.MessageFormat

class SplashActivity: AppCompatActivity(R.layout.activity_start) {

    var versionName: String = BuildConfig.VERSION_NAME
    private val mainScope = MainScope()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splash_version_app.text = MessageFormat.format(
            "{0}{1}",
            "version: ",
            versionName
        )

        mainScope.launch {
            delay(2000)
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}