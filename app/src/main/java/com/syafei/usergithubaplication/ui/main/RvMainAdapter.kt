package com.syafei.usergithubaplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.databinding.ListItemUserBinding

class RvMainAdapter(private val users: ArrayList<User>) :
    RecyclerView.Adapter<AdapterViewHolder>() {

    private var findUsers: ArrayList<User> = users

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val binding =
            ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val listUser = findUsers[position]
        Glide.with(holder.itemView.context)
            .load(listUser.avatar)
            .into(holder.binding.ivListItemProfile)

        with(holder) {
            binding.tvItemName.text = listUser.name
            binding.tvItemUsernames.text = listUser.userName
        }
    }

    override fun getItemCount(): Int = findUsers.size
}