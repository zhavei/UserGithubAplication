package com.syafei.usergithubaplication.ui.main.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.usergithubaplication.R
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.data.source.localdatabase.UserEntity
import com.syafei.usergithubaplication.databinding.ActivityFavoriteBinding
import com.syafei.usergithubaplication.ui.details.UserDetailActivity
import com.syafei.usergithubaplication.ui.main.RvMainAdapter
import com.syafei.usergithubaplication.ui.main.darkmode.DarkModeActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel>()
    private lateinit var userFavoriteAdapter: RvMainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        if (supportActionBar != null) {
            (supportActionBar)?.title = "User Fovorite"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        setupAppBar()

    }

    private fun setupRecyclerView() {
        userFavoriteAdapter = RvMainAdapter()
        userFavoriteAdapter.notifyDataSetChanged()

        userFavoriteAdapter.setOnItemClickCallBack(object : RvMainAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, UserDetailActivity::class.java).also { intent ->
                    intent.putExtra(UserDetailActivity.USER_ID, data.id)
                    intent.putExtra(UserDetailActivity.USER_NAME, data.username)
                    intent.putExtra(UserDetailActivity.USER_AVATAR_URL, data.username)
                    intent.putExtra(UserDetailActivity.USER_HTML_URL, data.username)
                    startActivity(intent)
                }

            }
        })

        binding.apply {
            rvUserFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserFavorite.adapter = userFavoriteAdapter
            rvUserFavorite.setHasFixedSize(true)
        }

        //database
        viewModel.getUserFavorite()?.observe(this) {
            if (it != null) {
                val list = mappingList(it)
                userFavoriteAdapter.setListUser(list)

                if (list.isEmpty()) {
                    binding.notFoundFavorite.visibility = View.VISIBLE
                } else {
                    binding.notFoundFavorite.visibility = View.GONE
                }
            }
        }
    }

    private fun mappingList(users: List<UserEntity>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.username,
                user.avatarUrl,
                user.htmlUrl,
                user.id
            )
            listUser.add(userMapped)
        }
        return listUser
    }

    private fun setupAppBar() {
        binding.appBarMainFavorite.toolbarMainFavorite.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.darkmode -> {
                    val intent = Intent(this, DarkModeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}