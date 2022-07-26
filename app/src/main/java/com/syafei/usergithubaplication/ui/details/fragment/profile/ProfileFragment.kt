package com.syafei.usergithubaplication.ui.details.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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

                    //send data to following followers fragment
                    bundle.putInt(UserDetailActivity.USER_FOLLOWING, detailUserRespon.following)
                    bundle.putInt(UserDetailActivity.USER_FOLLOWERS, detailUserRespon.followers)

                    binding.apply {
                        tvDetailName.text = detailUserRespon.name
                        tvDetailUsernames.text = detailUserRespon.htmlUrl
                        tvDetailRepository.text = detailUserRespon.publicRepos.toString()
                        tvDetailCompany.text = detailUserRespon.company
                        tvDetailLocation.text = detailUserRespon.location
                        tvDetailBlog.text = detailUserRespon.blog
                        tvFollowersRepository.text = detailUserRespon.followers.toString()
                        tvFollowingRepository.text = detailUserRespon.following.toString()

                        Glide.with(this@ProfileFragment).load(detailUserRespon.avatarUrl)
                            .centerCrop().into(ivDetailItemProfile)
                    }
                }
            }
        }

        //region check user on database and set toggle on off status
        binding.apply {

            var isUserChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                val chekUserOnDb = profileDetailViewModel.chekUsers(userId)
                withContext(Dispatchers.Main) {
                    if (chekUserOnDb != null) {
                        if (chekUserOnDb > 0) {
                            //set toggle on status when user exist in DB
                            toggleFavorite.isChecked = true
                            isUserChecked = true
                        } else {
                            //set toggle off status when user isn't exist in DB
                            toggleFavorite.isChecked = false
                            isUserChecked = false
                        }
                    }
                }
            }

            //database add to favorite
            toggleFavorite.setOnClickListener {
                //check if toggle is off displayed then mean toggle is - "true" status
                isUserChecked = !isUserChecked

                if (isUserChecked) {
                    profileDetailViewModel.addToFavorite(
                        userName,
                        detailUserResponse.avatarUrl,
                        detailUserResponse.htmlUrl,
                        userId
                    )
                    Snackbar.make(requireView(), "Added to Favorite", Snackbar.LENGTH_SHORT).show()
                } else {
                    profileDetailViewModel.removeFavorite(userId)
                    Snackbar.make(requireView(), "User Removed", Snackbar.LENGTH_SHORT).show()
                }
                toggleFavorite.isChecked =
                        /*set toggle to check/uncheck status :
                        if toggle is on uncheck status then toggle is checked to true "checked toggle"
                        and then otherwise*/
                    isUserChecked
            }
        }
        //endregion
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

}