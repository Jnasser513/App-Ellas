package com.app.appellas.ui.views.fragments.user.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.appellas.AppApplication
import com.app.appellas.R
import com.app.appellas.data.network.UIState
import com.app.appellas.databinding.VerifyRecoverCodeFragmentBinding
import com.app.appellas.viewmodel.ViewModelFactory
import com.app.appellas.viewmodel.user.AuthViewModel

class VerifyRecoveryCodeFragment: Fragment() {

    private var mBinding: VerifyRecoverCodeFragmentBinding? = null
    private val binding get() = mBinding!!

    private val userApp by lazy {
        activity?.application as AppApplication
    }

    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory(userApp.loginRepository)
    }

    private val args: VerifyRecoveryCodeFragmentArgs by navArgs()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.enterEmailFragment)
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
        mBinding = VerifyRecoverCodeFragmentBinding.inflate(inflater, container, false)
            .apply {
                viewmodel = authViewModel
                lifecycleOwner = viewLifecycleOwner
            }

        binding.recoveryCode1.doAfterTextChanged { message ->
            authViewModel.recoveryCode1.value = message.toString()
        }

        binding.recoveryCode2.doAfterTextChanged { message ->
            authViewModel.recoveryCode2.value = message.toString()
        }

        binding.recoveryCode3.doAfterTextChanged { message ->
            authViewModel.recoveryCode3.value = message.toString()
        }

        binding.recoveryCode4.doAfterTextChanged { message ->
            authViewModel.recoveryCode4.value = message.toString()
        }

        binding.recoveryCode5.doAfterTextChanged { message ->
            authViewModel.recoveryCode5.value = message.toString()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recoveryCodeEmailTitle.text = args.email
        setUpListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        observeState()
    }

    private fun observeState() {
        authViewModel.stateUI.observe(viewLifecycleOwner) { uiState ->
            handleUIState(uiState)
        }
    }

    private fun handleUIState(uiState: UIState<Int>?) {
        when(uiState) {
            is UIState.Loading -> {
                showProgressBar()
            }
            is UIState.Success -> {
                endShowProgressBar()
                Log.d("SUccess", "SUCCESS")
                val code = binding.recoveryCode1.text.toString().toInt() + binding.recoveryCode2.text.toString().toInt() +
                        binding.recoveryCode3.text.toString().toInt() + binding.recoveryCode4.text.toString().toInt() +
                        binding.recoveryCode5.text.toString().toInt()
                val direction = VerifyRecoveryCodeFragmentDirections
                    .actionVerifyRecoveryCodeFragmentToChangePasswordFragment(code.toString())
                findNavController().navigate(direction)
            }
            is UIState.Error -> {
                endShowProgressBar()
                Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
                //activity?.applicationContext?.let { showToast.showToast(it, layout, toastText, uiState.message) }
            }
            else -> {

            }
        }
    }

    private fun endShowProgressBar() {
        binding.recoveryCodeActionSend.isClickable = true
        binding.recoveryCodeProgressBar.visibility = View.GONE
        binding.recoveryCodeActionSend.setTextColor(resources.getColor(R.color.secondary_color))
    }

    private fun showProgressBar() {
        binding.recoveryCodeActionSend.isClickable = false
        binding.recoveryCodeProgressBar.visibility = View.VISIBLE
        binding.recoveryCodeActionSend.setTextColor(resources.getColor(android.R.color.transparent))
    }

    private fun setUpListeners() {
        verifyListener()

        binding.recoveryCodeActionReturn.setOnClickListener {
            val direction = VerifyRecoveryCodeFragmentDirections
                .actionVerifyRecoveryCodeFragmentToEnterEmailFragment()
            findNavController().navigate(direction)
        }
    }

    private fun verifyListener() {
        binding.recoveryCodeActionSend.setOnClickListener {
            authViewModel.verifyCode(args.email)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}