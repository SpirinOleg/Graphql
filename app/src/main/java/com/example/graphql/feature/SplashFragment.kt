package com.example.graphql.feature

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.graphql.BuildConfig
import com.example.graphql.R
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.coroutines.*
import java.text.MessageFormat
import kotlin.coroutines.CoroutineContext

class SplashFragment: Fragment(R.layout.activity_start), CoroutineScope {

    private lateinit var navController: NavController

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    var versionName: String = BuildConfig.VERSION_NAME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = findNavController()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        splash_version_app.text = MessageFormat.format(
            "{0}{1}",
            "version: ",
            versionName
        )

        launch {
            delay(3000)
            withContext(Dispatchers.Main){
                navController.navigate(R.id.action_start_activity_to_auth_fragment,null,NavOptions.Builder().setPopUpTo(R.id.start_activity,true).build())
            }
        }
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }
}