package com.example.graphql.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.ChecklistsQuery
import com.example.graphql.apolloClient
import com.example.graphql.databinding.ChecklistLayoutBinding

class ChecklistFragment : Fragment() {

    private lateinit var binding: ChecklistLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChecklistLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val checklists = mutableListOf<ChecklistsQuery.Data1>()
        val adapter = ChecklistAdapter(checklists)
        binding.checklists.layoutManager = LinearLayoutManager(requireContext())
        binding.checklists.adapter = adapter

        binding.checklists.addItemDecoration( DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL

        ))



        lifecycleScope.launchWhenResumed{
            val response =try {
                apolloClient(requireContext()).query(ChecklistsQuery()).await()
            } catch (e: ApolloException) {
                return@launchWhenResumed
            }

            val newChecklists =response.data?.checklists?.data

            if (newChecklists != null) {
                checklists.addAll(newChecklists)
                adapter.notifyDataSetChanged()
            }

        }

    }
}