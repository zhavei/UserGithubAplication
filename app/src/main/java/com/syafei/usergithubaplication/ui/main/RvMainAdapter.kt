package com.syafei.usergithubaplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.databinding.ListItemUserBinding
import java.util.*

class RvMainAdapter(private val users: ArrayList<User>) :
    RecyclerView.Adapter<AdapterViewHolder>(), Filterable {

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

        }
    }

    override fun getItemCount(): Int {
        return filterUser.size
    }

    interface OnitemClickCallBack {
        fun onItemClicked(data: User)
    }

    //region filter method
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charSearch = charSequence.toString()
                if (charSearch.isEmpty()) {
                    filterUser = users
                } else {
                    val resultList = ArrayList<User>()
                    for (row in users) {
                        if (row.name?.lowercase(Locale.ROOT)
                                ?.contains(charSearch.lowercase(Locale.ROOT)) == true
                        ) {
                            resultList.add(row)
                        }
                    }
                    filterUser = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterUser
                return filterResults
            }

            override fun publishResults(constraints: CharSequence?, results: FilterResults?) {
                filterUser = results?.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }
    //endregion
}