package com.syafei.usergithubaplication.ui.details.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.syafei.usergithubaplication.data.model.DetailUserResponse
import com.syafei.usergithubaplication.databinding.FragmentProfileBinding
import com.syafei.usergithubaplication.ui.details.UserDetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileDetailViewModel: ProfileDetailViewModel
    private lateinit var detailUserResponse: DetailUserResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressbar(true)

        val bundle = Bundle()
        val userName =
            requireActivity().intent.getStringExtra(UserDetailActivity.USER_NAME).toString()

        val userId = requireActivity().intent.getIntExtra(UserDetailActivity.USER_ID, 0)
        val avatarUrl = requireActivity().intent.getStringExtra(UserDetailActivity.USER_AVATAR_URL)
        val htmlUrl = requireActivity().intent.getStringExtra(UserDetailActivity.USER_HTML_URL)
        val listSearch =
            requireActivity().intent.getStringArrayListExtra(UserDetailActivity.USER_LIST_SEARCH)

        binding.apply {
            profileDetailViewModel =
                ViewModelProvider(requireActivity())[ProfileDetailViewModel::class.java]
            profileDetailViewModel.setupUserDetails(userName)

            profileDetailViewModel.isLoading.observe(requireActivity()) { load ->
                showProgressbar(load)
            }
            profileDetailViewModel.onFailure.observe(requireActivity()) { fail ->
                notFound(fail)
            }

            profileDetailViewModel.getDetailuser().observe(requireActivity()) { detailUserRespon ->
                if (detailUserRespon != null) {
                    detailUserResponse = detailUserRespon

                    //send data to following followers fragmen
                    bundle.putInt(UserDetailActivity.USER_FOLLOWING, detailUserRespon.following)
                    bundle.putInt(UserDetailActivity.USER_FOLLOWERS, detailUserRespon.followers)

                    binding.apply {
                        tvDetailName.text = detailUserRespon.name
                        tvDetailUsernames.text = detailUserRespon.htmlUrl
                        tvDetailRepository.text = detailUserRespon.publicRepos.toString()
                        tvDetailCompany.text = detailUserRespon.company ?: "none"
                        tvDetailLocation.text = detailUserRespon.location ?: "none"
                        tvDetailBlog.text = detailUserRespon.blog ?: "none"
                        tvFollowersRepository.text = detailUserRespon.followers.toString()
                        tvFollowingRepository.text = detailUserRespon.following.toString()

                        Glide.with(this@ProfileFragment).load(detailUserRespon.avatarUrl)
                            .centerCrop().into(ivDetailItemProfile)
                    }
                }
            }

            //database
            var isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = profileDetailViewModel.chekUsers(userId)
                withContext(Dispatchers.Main) {
                    if (count != null) {
                        if (count > 0) {
                            toggleFavorite.isChecked = true
                            isChecked = true
                        } else {
                            toggleFavorite.isChecked = false
                            isChecked = false
                        }
                    }
                }
            }

            //database
            toggleFavorite.setOnClickListener {
                isChecked = !isChecked
                if (isChecked) {
                    profileDetailViewModel.addToFavorite(
                        userName,
                        avatarUrl!!,
                        htmlUrl!!,
                        userId,
                        listSearch!!
                    )
                } else {
                    profileDetailViewModel.removeFavorite(userId)
                }
                toggleFavorite.isChecked = isChecked
            }

        }

    }

    private fun showProgressbar(progres: Boolean) {
        if (progres) {
            binding.progressBarDetail.visibility = View.VISIBLE
        } else {
            binding.progressBarDetail.visibility = View.GONE
        }
    }

    private fun notFound(state: Boolean) {
        if (state) {
            binding.tvNotfoundDetail.visibility = View.VISIBLE
        } else {
            binding.tvNotfoundDetail.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }
}