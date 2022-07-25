package com.syafei.usergithubaplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syafei.usergithubaplication.data.source.localdatabase.UserEntity
import com.syafei.usergithubaplication.databinding.ListItemUserBinding

class RvMainAdapter : RecyclerView.Adapter<RvMainAdapter.AdapterViewHolder>() {

    private val listUser = ArrayList<UserEntity>()
    fun setListUser(users: List<UserEntity>) {
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    private var onItemClickCallBack: OnItemClickCallBack? = null
    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val binding =
            ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class AdapterViewHolder(private var binding: ListItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            binding.root.setOnClickListener {
                onItemClickCallBack?.onItemClicked(user)
            }

            binding.apply {
                Glide.with(itemView).load(user.avatarUrl)
                    .centerCrop()
                    .into(ivListItemProfile)
                tvItemName.text = user.username
                tvItemUsernames.text = user.htmlUrl
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: UserEntity)
    }
}
