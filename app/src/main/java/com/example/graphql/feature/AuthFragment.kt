package com.example.graphql.feature

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.example.LoginMutation
import com.example.graphql.R
import com.example.graphql.apolloClient
import kotlinx.android.synthetic.main.auth_fragment.*
import java.lang.Exception

class AuthFragment : Fragment(R.layout.auth_fragment) {

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
            }
        }
    }


}