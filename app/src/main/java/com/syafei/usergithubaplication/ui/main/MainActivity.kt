package com.syafei.usergithubaplication.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.usergithubaplication.R
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.databinding.ActivityMainBinding
import com.syafei.usergithubaplication.ui.details.UserDetailActivity
import com.syafei.usergithubaplication.ui.main.darkmode.DarkModeActivity


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

        binding.rvMainActivity.setHasFixedSize(true)

        addListItem()
        setupRecyclerView()
        searchUser()
        setupAppBar()

    }

    //region Search User
    private fun searchUser() {
        val countrySearch = binding.svMain
        val searchIcon =
            countrySearch.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLACK)
        val cancelIcon =
            countrySearch.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.BLACK)
        val textViewSearch =
            countrySearch.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        textViewSearch.setTextColor(Color.BLACK)

        countrySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter.filter.filter(newText)
                return false
            }

        })
    }
    // endregion Search User
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


    private fun setupAppBar() {
        binding.appBarMain.toolbarMain.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    val intentFavorite = Intent(this, DarkModeActivity::class.java)
                    startActivity(intentFavorite)
                    true
                }
                R.id.darkmode -> {
                    val intentFavorite = Intent(this, DarkModeActivity::class.java)
                    startActivity(intentFavorite)
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Github App")
            .setMessage("Wanna Leave?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which -> finish() })
            .setNegativeButton("No", null)
            .show()
    }

}