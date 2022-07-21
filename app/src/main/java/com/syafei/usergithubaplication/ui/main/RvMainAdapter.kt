package com.syafei.usergithubaplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.databinding.ListItemUserBinding

class RvMainAdapter : RecyclerView.Adapter<RvMainAdapter.AdapterViewHolder>() {

    private val listUser = ArrayList<User>()
    fun setListUser(users: ArrayList<User>) {
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
        /*val listUsers = listUser[position]
        Glide.with(holder.itemView.context)
            .load(listUsers.avatarUrl)
            .into(holder.binding.ivListItemProfile)

        with(holder) {
            binding.tvItemName.text = listUsers.username
            binding.tvItemUsernames.text =
                holder.itemView.context.getString(R.string.path_urls) + listUsers.username

            holder.itemView.setOnClickListener {
                onItemClickCallBack?.onItemClicked(listUser[holder.adapterPosition])
            }
        }*/
    }

    override fun getItemCount(): Int = listUser.size

    inner class AdapterViewHolder(private var binding: ListItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallBack?.onItemClicked(user)
            }

            binding.apply {
                Glide.with(itemView).load(user.avatarUrl).into(ivListItemProfile)
                tvItemName.text = user.username
                tvItemUsernames.text = user.htmlUrl
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: User)
    }
}
