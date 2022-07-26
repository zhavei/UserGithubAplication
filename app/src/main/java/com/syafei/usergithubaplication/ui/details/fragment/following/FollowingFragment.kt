package com.syafei.usergithubaplication.ui.details.fragment.following

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.databinding.FragmentFollowBinding
import com.syafei.usergithubaplication.ui.details.UserDetailActivity
import com.syafei.usergithubaplication.ui.details.fragment.FollowsAdapter

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: FollowsAdapter
    private lateinit var userName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userName = requireActivity().intent.getStringExtra(UserDetailActivity.USER_NAME).toString()
        val followingNumber =
            requireActivity().intent.getIntExtra(UserDetailActivity.USER_FOLLOWING, 0)

        adapter = FollowsAdapter()

        binding.apply {
            rvFollow.setHasFixedSize(true)
            rvFollow.layoutManager = LinearLayoutManager(activity)
            rvFollow.adapter = adapter

            if (followingNumber == 0) {
                tvNotfoundFollow.visibility = View.GONE
            } else {
                tvNotfoundFollow.visibility = View.VISIBLE            }
        }

        showProgressbar(true)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowingViewModel::class.java]

        viewModel.setFollowingList(userName)
        viewModel.getListFollowings().observe(viewLifecycleOwner) { followingList ->
            if (followingList != null) {
                adapter.setListFollow(followingList)
                showProgressbar(false)
            }
        }

        adapter.setOnItemClickCallBack(object : FollowsAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: User) {
                Intent(requireContext(), UserDetailActivity::class.java).also { intent ->
                    intent.putExtra(UserDetailActivity.USER_NAME, data.username)
                    intent.putExtra(UserDetailActivity.USER_ID, data.id)
                    intent.putExtra(UserDetailActivity.USER_HTML_URL, data.htmlUrl)
                    intent.putExtra(UserDetailActivity.USER_AVATAR_URL, data.avatarUrl)
                    startActivity(intent)
                }
            }
        })
    }

    private fun showProgressbar(progres: Boolean) {
        if (progres) {
            binding.progressBarFollow.visibility = View.VISIBLE
        } else {
            binding.progressBarFollow.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}