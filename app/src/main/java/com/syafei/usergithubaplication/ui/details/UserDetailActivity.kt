package com.syafei.usergithubaplication.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.syafei.usergithubaplication.R
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.databinding.ActivityDetailUserBinding


class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val USER = "name_"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            (supportActionBar)?.title = "User Details"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //showUserDetails()

    }

    /*private fun showUserDetails() {
        val getUser = intent.getParcelableExtra<User>(USER) as User
        val image = getUser.avatar
        Glide.with(this).load(image).into(binding.ivDetailItemProfile)
        binding.tvDetailName.text = getUser.name.toString()
        binding.tvDetailUsernames.text =
            resources.getString(R.string.path_urls) + getUser.userName.toString()
        binding.tvDetailCompany.text = getUser.company.toString()
        binding.tvDetailLocation.text = getUser.location.toString()
        binding.tvDetailRepository.text = getUser.repository.toString()
        binding.tvFollowersRepository.text = getUser.followers.toString()
        binding.tvFollowingRepository.text = getUser.following.toString()
    }*/

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(
            androidx.appcompat.R.anim.abc_fade_in,
            androidx.appcompat.R.anim.abc_fade_out
        )
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            androidx.appcompat.R.anim.abc_fade_in,
            androidx.appcompat.R.anim.abc_fade_out
        )
    }

}