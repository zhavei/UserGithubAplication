package com.syafei.usergithubaplication.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.usergithubaplication.R
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.databinding.ActivityMainBinding
import com.syafei.usergithubaplication.ui.details.UserDetailActivity
import com.syafei.usergithubaplication.ui.main.darkmode.DarkModeActivity


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<UserResultViewModel>()
    private lateinit var userResultAdapter: RvMainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupAppBar()
    }

    private fun setupRecyclerView() {
        userResultAdapter = RvMainAdapter()
        userResultAdapter.notifyDataSetChanged()

        userResultAdapter.setOnItemClickCallBack(object : RvMainAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, UserDetailActivity::class.java).also { intent ->
                    intent.putExtra(UserDetailActivity.USER_ID, data.username)
                    intent.putExtra(UserDetailActivity.USER_NAME, data.username)
                    intent.putExtra(UserDetailActivity.USER_AVATAR_URL, data.username)
                    intent.putExtra(UserDetailActivity.USER_HTML_URL, data.username)
                    startActivity(intent)

                }
            }
        })

        binding.apply {
            rvMainActivity.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMainActivity.adapter = userResultAdapter
            rvMainActivity.setHasFixedSize(true)

            etSearch.setOnKeyListener { view, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    findUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

        }

        viewModel.getSearchUser().observe(this) { view ->
            if (view != null) {
                userResultAdapter.setListUser(view)
                showProgressbar(false)
            }
        }
    }

    private fun findUser() {
        binding.apply {
            val query = etSearch.text.toString()
            if (query.isEmpty()) {
                notFound(true)
                return
            }
            notFound(false)
            showProgressbar(true)
            viewModel.searchUsers(query)
        }
    }

    private fun showProgressbar(progres: Boolean) {
        if (progres) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun notFound(state: Boolean) {
        if (state) {
            binding.tvNotfound.visibility = View.VISIBLE
        } else {
            binding.tvNotfound.visibility = View.GONE
        }
    }


    private fun setupAppBar() {
        binding.appBarMain.toolbarMain.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
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