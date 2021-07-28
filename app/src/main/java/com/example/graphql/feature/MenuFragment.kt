package com.example.graphql.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.graphql.R
import com.example.graphql.databinding.MenuFragmentBinding
import kotlinx.android.synthetic.main.menu_fragment.*

class MenuFragment: Fragment() {

    private lateinit var binding: MenuFragmentBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = MenuFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_cheklist.setOnClickListener {
            findNavController().navigate(R.id.action_menu_gragment_to_cheklist)
        }
    }
}