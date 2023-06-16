package com.alvintio.calcuwaste.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.airbnb.lottie.LottieAnimationView
import com.alvintio.calcuwaste.utils.Preference
import com.alvintio.calcuwaste.R
import com.alvintio.calcuwaste.databinding.FragmentRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var animView: LottieAnimationView
    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var pref: Preference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        pref = Preference(requireActivity())
        animView = binding.imageView
        animView.enableMergePathsForKitKatAndAbove(true)

        startAnimation()

        binding.btnAction.setOnClickListener {
            if (binding.edName.text?.length ?: 0 <= 0) {
                binding.edName.error = getString(R.string.required_field)
                binding.edName.requestFocus()
            } else if (binding.edEmail.text?.length ?: 0 <= 0) {
                binding.edEmail.error = getString(R.string.required_field)
                binding.edEmail.requestFocus()
            } else if (binding.edPassword.text?.length ?: 0 <= 0) {
                binding.edPassword.error = getString(R.string.required_field)
                binding.edPassword.requestFocus()
            } else if (binding.edName.error?.length ?: 0 > 0) {
                binding.edName.requestFocus()
            } else if (binding.edEmail.error?.length ?: 0 > 0) {
                binding.edEmail.requestFocus()
            } else if (binding.edPassword.error?.length ?: 0 > 0) {
                binding.edPassword.requestFocus()
            } else {
                val name = binding.edName.text.toString()
                val email = binding.edEmail.text.toString()
                val password = binding.edPassword.text.toString()
                viewModel.getRegister(name, email, password)
            }
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
                viewModel.register.observe(viewLifecycleOwner) {
                    if (!it.error) {
                        Toast.makeText(
                            activity, getString(R.string.success_register), Toast.LENGTH_SHORT
                        ).show()
                        parentFragmentManager.beginTransaction().apply {
                            replace(
                                R.id.frame_container,
                                LoginFragment(),
                                LoginFragment::class.java.simpleName
                            )
                            addToBackStack(null)
                            commit()
                        }
                    }
                }
                viewModel.error.observe(viewLifecycleOwner) {
                    it?.let {
                        if (it.isNotEmpty()) {
                            Toast.makeText(activity, getString(R.string.failed) + it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        }
    }

    private fun startAnimation() {
        val welcome = ObjectAnimator.ofFloat(binding.labelGreet, View.ALPHA, 1f).setDuration(500)
        val appName = ObjectAnimator.ofFloat(binding.labelAppName, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.labelAuth, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.edName, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.edEmail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.edPassword, View.ALPHA, 1f).setDuration(500)
        val btnRegister = ObjectAnimator.ofFloat(binding.btnAction, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginContainer, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(welcome, appName, register, name, email, password, btnRegister, login)
            start()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loading.root.visibility = View.VISIBLE
        } else {
            binding.loading.root.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}