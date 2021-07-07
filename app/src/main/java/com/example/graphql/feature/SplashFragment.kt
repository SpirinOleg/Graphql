package com.example.graphql.feature

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.graphql.BuildConfig
import com.example.graphql.MainActivity
import com.example.graphql.R
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.coroutines.*
import java.text.MessageFormat
import kotlin.coroutines.CoroutineContext

class SplashFragment: AppCompatActivity(), CoroutineScope {

    private lateinit var navController: NavController

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    var versionName: String = BuildConfig.VERSION_NAME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        splash_version_app.text = MessageFormat.format(
            "{0}{1}",
            "version: ",
            versionName
        )

        launch {
            delay(3000)
            withContext(Dispatchers.Main){
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }
}