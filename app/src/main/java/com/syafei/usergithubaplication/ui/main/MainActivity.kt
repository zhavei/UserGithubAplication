package com.syafei.usergithubaplication.ui.main

import android.content.res.TypedArray
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.usergithubaplication.R
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var mAvatar: TypedArray
    private lateinit var mName: Array<String>
    private lateinit var mUserName: Array<String>
    private lateinit var mCompany: Array<String>
    private lateinit var mLocation: Array<String>
    private lateinit var mRepository: Array<String>
    private lateinit var mFollowers: Array<String>
    private lateinit var mFollowing: Array<String>

    private var users: ArrayList<User> = arrayListOf()
    private var userAdapter = RvMainAdapter(users)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.rvMainActivity.setHasFixedSize(true)

        addListItem()
        setupRecyclerView()


    }

    private fun setupRecyclerView() {
        binding.rvMainActivity.layoutManager = LinearLayoutManager(this)
        binding.rvMainActivity.adapter = userAdapter
    }

    private fun getDataLocal() {
        mAvatar = resources.obtainTypedArray(R.array.avatar)
        mName = resources.getStringArray(R.array.name)
        mUserName = resources.getStringArray(R.array.username)
        mCompany = resources.getStringArray(R.array.company)
        mLocation = resources.getStringArray(R.array.location)
        mRepository = resources.getStringArray(R.array.repository)
        mFollowers = resources.getStringArray(R.array.followers)
        mFollowing = resources.getStringArray(R.array.following)
    }

    private fun addListItem(): ArrayList<User> {
        getDataLocal()
        for (position in mName.indices) {
            val userLisy = User(
                mAvatar.getResourceId(position, -1),
                mName[position],
                mUserName[position],
                mCompany[position],
                mLocation[position],
                mRepository[position],
                mFollowers[position],
                mFollowing[position]
            )
            users.add(userLisy)
        }
        mAvatar.recycle()
        return users
    }
}