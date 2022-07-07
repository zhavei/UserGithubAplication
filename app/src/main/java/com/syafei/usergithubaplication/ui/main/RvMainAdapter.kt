package com.syafei.usergithubaplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.databinding.ListItemUserBinding

class RvMainAdapter(private val users: ArrayList<User>) :
    RecyclerView.Adapter<AdapterViewHolder>() {

    private lateinit var onitemToDetails: OnitemClickCallBack

    private var findUsers: ArrayList<User> = users

    //region filter
    private var filterUser = ArrayList<User>()
    init {
        filterUser = users
    }
    //endregion

    fun setOnItemClickCallBack(onitemClickCallBack: OnitemClickCallBack) {
        this.onitemToDetails = onitemClickCallBack
    }

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
            binding.tvItemUsernames.text = "https://github.com/" + listUser.userName

            holder.itemView.setOnClickListener {
                onitemToDetails.onItemClicked(findUsers[holder.adapterPosition])
            }

            //binding.tvItemName.text = filterUser[position]
        }
    }

    override fun getItemCount(): Int {
        return filterUser.size
    }

    interface OnitemClickCallBack {
        fun onItemClicked(data: User)
    }
}