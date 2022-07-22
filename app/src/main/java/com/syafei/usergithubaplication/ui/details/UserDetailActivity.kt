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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            (supportActionBar)?.title = "User Details"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupTabsViewPager()

    }

    private fun setupTabsViewPager() {
        val userName = intent.getStringExtra(USER_NAME)
        val userId = intent.getIntExtra(USER_ID, 0)
        val avatarUser = intent.getStringExtra(USER_AVATAR_URL)
        val userLink = intent.getStringExtra(USER_HTML_URL)

        val bundle = Bundle()
        bundle.putString(USER_NAME, userName)
        bundle.putInt(USER_ID, userId)

        //custom color tab
        binding.tabLayout.setSelectedTabIndicatorColor(Color.RED)
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_bold))
        binding.tabLayout.tabTextColors =
            ContextCompat.getColorStateList(this, android.R.color.white)
        val numberOfTabs = 3
        binding.tabLayout.tabMode = TabLayout.MODE_FIXED

        val tabsAdapter = TabsPagerAdapter(
            numberOfTabs,
            this@UserDetailActivity,
            bundle
        )
        binding.viewPagerDetails.adapter = tabsAdapter
        binding.viewPagerDetails.isUserInputEnabled = true    // Enable Swipe

        TabLayoutMediator(binding.tabLayout, binding.viewPagerDetails) { tab, posisition ->
            when (posisition) {
                0 -> {
                    tab.text = "Profile"
                    tab.setIcon(R.drawable.ic_baseline_person_24)
                }
                1 -> {
                    tab.text = "Following"
                    tab.setIcon(R.drawable.ic_baseline_person_add_24)
                }
                2 -> {
                    tab.text = "Followers"
                    tab.setIcon(R.drawable.ic_baseline_3p_24)
                }
            }
            tab.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                Color.WHITE, BlendModeCompat.SRC_ATOP
            )
        }.attach()
    }

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

    companion object {
        const val USER_NAME = "_username"
        const val USER_FOLLOWERS = "_followers"
        const val USER_FOLLOWING = "_following"
        const val USER_ID = "_id"
        const val USER_AVATAR_URL = "_avatar_url"
        const val USER_HTML_URL = "_html_url"
    }

}