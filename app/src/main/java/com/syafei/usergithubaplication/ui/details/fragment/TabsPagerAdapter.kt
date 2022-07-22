package com.syafei.usergithubaplication.ui.details.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.syafei.usergithubaplication.ui.details.fragment.followers.FollowersFragment
import com.syafei.usergithubaplication.ui.details.fragment.following.FollowingFragment
import com.syafei.usergithubaplication.ui.details.fragment.profile.ProfileFragment

class TabsPagerAdapter(
    private var numberOfTabs: Int,
    activity: AppCompatActivity,
    data: Bundle
) : FragmentStateAdapter( activity) {

    private var fragmentBundle: Bundle = data

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ProfileFragment()
            1 -> fragment = FollowingFragment()
            2 -> fragment = FollowersFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}
