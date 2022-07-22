package com.syafei.usergithubaplication.ui.details.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.databinding.ListItemFollowBinding

class FollowsAdapter : RecyclerView.Adapter<FollowsAdapter.AdapterViewHolder>() {

    private val listUserFollow = ArrayList<User>()
    fun setListFollow(users: ArrayList<User>) {
        listUserFollow.clear()
        listUserFollow.addAll(users)
        notifyDataSetChanged()
    }

    private var onItemClickCallBack: OnItemClickCallBack? = null
    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val binding =
            ListItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(listUserFollow[position])
    }

    override fun getItemCount(): Int = listUserFollow.size

    inner class AdapterViewHolder(private var binding: ListItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {

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
        fun onItemClicked(data: User)
    }
}