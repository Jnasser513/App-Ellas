package com.app.appellas.ui.views.fragments.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.appellas.R
import com.app.appellas.databinding.HomepageFragmentBinding

class HomeFragment: Fragment() {

    private var mBinding: HomepageFragmentBinding? = null
    private val binding get() = mBinding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = HomepageFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpNavigationListeners()
    }

    private fun setUpNavigationListeners() {
        navToBlogFragment()
        navToAdvisoryFragment()
        navToInformationFragment()
    }

    private fun navToBlogFragment() {
        binding.homepageBlogContainer.setOnClickListener {
            val navController = findNavController()
            val direction = HomeFragmentDirections
                .actionHomeFragmentToBlogsFragment()
            navController.navigate(direction)
        }
    }

    private fun navToAdvisoryFragment() {
        binding.homepageAdvisoryContainer.setOnClickListener {
            val navController = findNavController()
            val direction = HomeFragmentDirections
                .actionHomeFragmentToAdvisoryFragment()
            navController.navigate(direction)
        }
    }

    private fun navToInformationFragment() {
        binding.homepageInformationContainer.setOnClickListener {
            val navController = findNavController()
            val direction = HomeFragmentDirections
                .actionHomeFragmentToInformationFragment()
            navController.navigate(direction)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}