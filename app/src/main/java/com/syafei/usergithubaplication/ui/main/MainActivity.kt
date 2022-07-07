package com.syafei.usergithubaplication.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.usergithubaplication.R
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.databinding.ActivityMainBinding
import com.syafei.usergithubaplication.ui.details.UserDetailActivity


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
        //searchUser()


    }

    /*private fun searchUser() {
        binding.etSearchMain.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //not Use
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //not use
            }

            override fun afterTextChanged(editable: Editable) {
                val searchKey = editable.toString()
                users.clear()
                for (user: User in users) {
                    val usersList: String? = user.name?.toLowerCase()
                    if (usersList != null) {
                        if (usersList.contains(searchKey.toLowerCase())) {
                            users.add(user)
                        }
                    }
                }
                userAdapter.notifyDataSetChanged()
            }
        })
    }*/

    private fun setupRecyclerView() {
        binding.rvMainActivity.layoutManager = LinearLayoutManager(this)
        binding.rvMainActivity.adapter = userAdapter

        userAdapter.setOnItemClickCallBack(object : RvMainAdapter.OnitemClickCallBack {
            override fun onItemClicked(data: User) {
                moveToDetails(data)
            }
        })
    }

    private fun moveToDetails(data: User) {
        val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.USER, data)
        startActivity(intent)
        overridePendingTransition(
            androidx.appcompat.R.anim.abc_fade_in,
            androidx.appcompat.R.anim.abc_fade_out
        )
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
            val userListy = User(
                mAvatar.getResourceId(position, -1),
                mName[position],
                mUserName[position],
                mCompany[position],
                mLocation[position],
                mRepository[position],
                mFollowers[position],
                mFollowing[position]
            )
            users.add(userListy)
        }
        mAvatar.recycle()
        return users
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Github App")
            .setMessage("beneran mau pergi?")
            .setPositiveButton("iya",
                DialogInterface.OnClickListener { dialog, which -> finish() })
            .setNegativeButton("engga", null)
            .show()
    }

}