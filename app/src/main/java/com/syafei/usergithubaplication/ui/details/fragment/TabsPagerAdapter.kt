package com.syafei.usergithubaplication.ui.details.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private var numberOfTabs: Int
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "Profile Fragment")
                val fragment = ProfileFragment()
                fragment.arguments = bundle
                return fragment
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "Following Fragment")
                val fragment = ProfileFragment()
                fragment.arguments = bundle
                return fragment
            }
            2 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "Followers Fragment")
                val fragment = ProfileFragment()
                fragment.arguments = bundle
                return fragment
            }
            else -> return fragment as Fragment
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}
