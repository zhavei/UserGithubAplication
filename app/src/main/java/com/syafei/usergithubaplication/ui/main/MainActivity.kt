package com.syafei.usergithubaplication.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.usergithubaplication.R
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.databinding.ActivityMainBinding
import com.syafei.usergithubaplication.ui.details.UserDetailActivity
import com.syafei.usergithubaplication.ui.main.darkmode.SettingDataStore
import com.syafei.usergithubaplication.ui.main.darkmode.UIMode
import kotlinx.coroutines.launch


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

    private lateinit var settingDataStore: SettingDataStore

    private var users: ArrayList<User> = arrayListOf()
    private var userAdapter = RvMainAdapter(users)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.rvMainActivity.setHasFixedSize(true)
        settingDataStore = SettingDataStore(this)

        addListItem()
        setupRecyclerView()
        searchUser()

        //region Dark Mode
        observeUIPreferences()
        initViewSwitch()
        //endregion

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

    //region Dark Mode
    private fun observeUIPreferences() {
        settingDataStore.uIModeFLow.asLiveData().observe(this) { uiMode ->
            setCheckedMode(uiMode)
        }
    }

    private fun setCheckedMode(uiMode: UIMode?) {
        if (uiMode == UIMode.LIGHT) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.switchMain.isChecked = false
        }
        else if (uiMode == UIMode.DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.switchMain.isChecked = true
        }
    }

    private fun initViewSwitch(){
        binding.switchMain.setOnCheckedChangeListener{ _, isChecked ->
            lifecycleScope.launch{
                when(isChecked){
                    true -> settingDataStore.setDarkMode(UIMode.DARK)
                    false -> settingDataStore.setDarkMode(UIMode.LIGHT)
                }
            }
        }
    }
    //endregion

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