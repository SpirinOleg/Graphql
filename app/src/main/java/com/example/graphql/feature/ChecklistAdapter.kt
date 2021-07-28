package com.example.graphql.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ChecklistsQuery
import com.example.graphql.databinding.ChecklistItemBinding

class ChecklistAdapter(val checklist: List<ChecklistsQuery.Data1>) : RecyclerView.Adapter<ChecklistAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChecklistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chelist = checklist.get(position)
        holder.binding.cheklistText.text = chelist.name
    }

    override fun getItemCount(): Int {
        return checklist.size
    }

    class ViewHolder(val binding: ChecklistItemBinding) : RecyclerView.ViewHolder(binding.root)

}