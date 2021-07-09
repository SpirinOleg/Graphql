package com.example.graphql.feature

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.graphql.databinding.AuthFragmentBinding
import java.lang.Exception

class AuthFragment() : Fragment() {

    private lateinit var binding: AuthFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AuthFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitProgressBar.visibility = View.GONE
        binding.btnLogin.setOnClickListener {
            val txtemail = binding.email.text.toString()
            val txtpassword = binding.password.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(txtemail).matches()) {
                binding.emailLayout.error = getString(R.string.invalid_email)
                return@setOnClickListener
            }

            binding.submitProgressBar.visibility = View.VISIBLE
            binding.btnLogin.visibility = View.GONE
            lifecycleScope.launchWhenResumed {
                val response = try {
                    apolloClient(requireContext()).mutate(
                            LoginMutation(
                                    email = Input.fromNullable(txtemail),
                                    password = Input.fromNullable(txtpassword)
                            )
                    ).await()
                } catch (e: Exception) {
                    null
                }

                val login = response?.data?.login
                if (login == null || response.hasErrors()) {
                    binding.submitProgressBar.visibility = View.GONE
                    binding.btnLogin.visibility = View.VISIBLE
                    return@launchWhenResumed
                }
                User.setToken(requireContext(), login)
                findNavController().navigate(R.id.action_auth_fragment_to_menu_gragment)
            }
        }
    }


}