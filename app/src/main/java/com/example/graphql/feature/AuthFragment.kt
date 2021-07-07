package com.example.graphql.feature

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.example.LoginMutation
import com.example.graphql.R
import com.example.graphql.User
import com.example.graphql.apolloClient
import kotlinx.android.synthetic.main.auth_fragment.*
import java.lang.Exception

class AuthFragment() : Fragment(R.layout.auth_fragment) {

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submit_progress_bar.visibility = View.GONE
        btn_login.setOnClickListener {
            val txtemail = email.text.toString()
            val txtpassword = password.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(txtemail).matches()) {
                email_layout.error = getString(R.string.invalid_email)
                return@setOnClickListener
            }

            submit_progress_bar.visibility = View.VISIBLE
            btn_login.visibility = View.GONE
            lifecycleScope.launchWhenResumed {
                val response = try {
                    apolloClient(requireContext()).mutate(
                        LoginMutation(
                            email = Input.fromNullable(
                                txtemail
                            ).toString(),
                            password = Input.fromNullable(
                                txtpassword
                            ).toString()
                        )
                    ).await()
                } catch (e: Exception) {
                    null
                }

                val login =response?.data?.login
                if(login == null || response.hasErrors()) {
                    submit_progress_bar.visibility = View.GONE
                    btn_login.visibility = View.VISIBLE
                    return@launchWhenResumed
                }

                User.setToken(requireContext(), login)
//                findNavController().popBackStack()
            }
        }
    }


}