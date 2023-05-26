package com.mobile.test.ui.screens.signup

import android.R.attr.data
import android.os.Bundle
import android.text.SpannableString
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mobile.test.R
import com.mobile.test.base.BaseFragment
import com.mobile.test.databinding.SignupFragmentBinding
import com.mobile.test.utils.PasswordLevel
import com.mobile.test.utils.isValidEmail
import com.mobile.test.utils.setSpannableStringWithAction


class SignupFragment: BaseFragment() {
    private lateinit var binding: SignupFragmentBinding
    private val mViewModel by lazy { viewModel as SignupViewModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.signup_fragment, container, false)
        val viewModelFactory = SignupViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[SignupViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mViewModel

        bindingView()
        observerViewModel()
        return binding.root
    }

    private fun bindingView() {
        val spannableString = SpannableString(binding.tvTermAndService.text)
        val textTermOfService = requireContext().getString(R.string.term_of_service)
        val textPrivacyPolicy = requireContext().getString(R.string.privacy_policy)

        spannableString.setSpannableStringWithAction(
            binding.tvTermAndService.text.toString(),
            textTermOfService,
            requireContext().getColor(R.color.flat_purple)
        ) {
            Toast.makeText(
                requireContext(), "Term Of Service",
                Toast.LENGTH_LONG
            ).show()
        }

        spannableString.setSpannableStringWithAction(
            binding.tvTermAndService.text.toString(),
            textPrivacyPolicy,
            requireContext().getColor(R.color.flat_purple)
        ) {
            Toast.makeText(
                requireContext(), "Privacy Policy",
                Toast.LENGTH_LONG
            ).show()
        }

        binding.tvTermAndService.setText(spannableString, TextView.BufferType.SPANNABLE)
        binding.tvTermAndService.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun observerViewModel() {
        mViewModel.isSignUpSuccess.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    mViewModel.navigate(SignupFragmentDirections.actionSignupFragmentToCategoriesFragment())
                }
            }
        }

        mViewModel.isEnableSignUp.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    binding.btnSignUp.colorFilter = null
                } else {
                    binding.btnSignUp.setColorFilter(requireContext().getColor(R.color.black_system))
                }
            }
        }

        mViewModel.email.observe(viewLifecycleOwner) {
            it?.let {
                mViewModel.onCheckEnableSignUp()
                if (it.isEmpty() || !it.isValidEmail()) {
                    binding.vwValidEmail.background = requireContext().getDrawable(R.drawable.bg_invalid_email)
                } else {
                    binding.vwValidEmail.background = requireContext().getDrawable(R.drawable.bg_valid_info)
                }
            }
        }

        mViewModel.password.observe(viewLifecycleOwner) {
            it?.let {
                mViewModel.onCheckEnableSignUp()
                mViewModel.onCheckPasswordLevel(it)
            }
        }

        mViewModel.passwordLevel.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    PasswordLevel.EMPTY.rawValue -> {
                        binding.tvPasswordLevel.text = ""
                        binding.vwValidPassword.background = requireContext().getDrawable(R.drawable.bg_invalid_email)
                    }
                    PasswordLevel.SHORT.rawValue -> {
                        binding.tvPasswordLevel.text = requireContext().getString(R.string.sign_up_password_too_short)
                        binding.tvPasswordLevel.setTextColor(ContextCompat.getColor(
                            requireContext(),
                            R.color.white_50
                        ))
                        binding.vwValidPassword.background = requireContext().getDrawable(R.drawable.bg_password_short)
                    }
                    PasswordLevel.LONG.rawValue -> {
                        binding.tvPasswordLevel.text = requireContext().getString(R.string.sign_up_password_too_long)
                        binding.tvPasswordLevel.setTextColor(ContextCompat.getColor(
                            requireContext(),
                            R.color.white_50
                        ))
                        binding.vwValidPassword.background = requireContext().getDrawable(R.drawable.bg_password_short)
                    }
                    PasswordLevel.WEEK.rawValue -> {
                        binding.tvPasswordLevel.text = requireContext().getString(R.string.sign_up_password_week)
                        binding.tvPasswordLevel.setTextColor(ContextCompat.getColor(
                            requireContext(),
                            R.color.flat_red
                        ))
                        binding.vwValidPassword.background = requireContext().getDrawable(R.drawable.bg_password_week)
                    }
                    PasswordLevel.FAIR.rawValue -> {
                        binding.tvPasswordLevel.text = requireContext().getString(R.string.sign_up_password_fair)
                        binding.tvPasswordLevel.setTextColor(ContextCompat.getColor(
                            requireContext(),
                            R.color.flat_yellow
                        ))
                        binding.vwValidPassword.background = requireContext().getDrawable(R.drawable.bg_password_fair)
                    }
                    PasswordLevel.GOOD.rawValue -> {
                        binding.tvPasswordLevel.text = requireContext().getString(R.string.sign_up_password_good)
                        binding.tvPasswordLevel.setTextColor(ContextCompat.getColor(
                            requireContext(),
                            R.color.flat_purple
                        ))
                        binding.vwValidPassword.background = requireContext().getDrawable(R.drawable.bg_password_good)
                    }
                    PasswordLevel.STRONG.rawValue -> {
                        binding.tvPasswordLevel.text = requireContext().getString(R.string.sign_up_password_strong)
                        binding.tvPasswordLevel.setTextColor(ContextCompat.getColor(
                            requireContext(),
                            R.color.flat_green
                        ))
                        binding.vwValidPassword.background = requireContext().getDrawable(R.drawable.bg_password_strong)
                    }
                }
            }
        }

        mViewModel.isShowLoginPassword.observe(viewLifecycleOwner) {
            if (it) {
                binding.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }
}