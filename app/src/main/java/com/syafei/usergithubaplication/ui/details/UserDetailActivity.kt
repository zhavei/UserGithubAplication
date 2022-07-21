package com.syafei.usergithubaplication.ui.details

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.syafei.usergithubaplication.R
import com.syafei.usergithubaplication.databinding.ActivityDetailUserBinding
import com.syafei.usergithubaplication.ui.details.fragment.TabsPagerAdapter


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

        setupTabsViewPager()

        //showUserDetails()

    }

    private fun setupTabsViewPager() {
        //custom color tab
        binding.tabLayout.setSelectedTabIndicatorColor(Color.RED)
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_bold))
        binding.tabLayout.tabTextColors =
            ContextCompat.getColorStateList(this, android.R.color.white)
        val numberOfTabs = 3
        binding.tabLayout.tabMode = TabLayout.MODE_FIXED

        val tabsAdapter = TabsPagerAdapter(supportFragmentManager, lifecycle, numberOfTabs)
        binding.viewPagerDetails.adapter = tabsAdapter
        binding.viewPagerDetails.isUserInputEnabled = true    // Enable Swipe

        TabLayoutMediator(binding.tabLayout, binding.viewPagerDetails) { tab, posisition ->
            when (posisition) {
                0 -> {
                    tab.text = "Profile"
                    tab.setIcon(R.drawable.ic_baseline_bedtime_24)
                }
                1 -> {
                    tab.text = "Following"
                    tab.setIcon(R.drawable.ic_baseline_favorite_24)
                }
                2 -> {
                    tab.text = "Followers"
                    tab.setIcon(R.drawable.ic_baseline_settings_24)
                }
            }
            tab.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                Color.WHITE, BlendModeCompat.SRC_ATOP
            )
        }.attach()
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