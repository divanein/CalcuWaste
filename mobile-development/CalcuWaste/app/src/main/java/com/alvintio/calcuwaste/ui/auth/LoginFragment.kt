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
import com.alvintio.calcuwaste.utils.Const.EMAIL
import com.alvintio.calcuwaste.utils.Const.IS_LOGIN
import com.alvintio.calcuwaste.utils.Const.NAME
import com.alvintio.calcuwaste.utils.Const.TOKEN
import com.alvintio.calcuwaste.utils.Const.USER_ID
import com.alvintio.calcuwaste.ui.MainActivity
import com.alvintio.calcuwaste.utils.Preference
import com.alvintio.calcuwaste.R
import com.alvintio.calcuwaste.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var pref: Preference
    private lateinit var animView: LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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
            if (binding.edEmail.text?.length ?: 0 <= 0) {
                binding.edEmail.error = getString(R.string.required_field)
                binding.edEmail.requestFocus()
            } else if (binding.edPassword.text?.length ?: 0 <= 0) {
                binding.edPassword.error = getString(R.string.required_field)
                binding.edPassword.requestFocus()
            } else if (binding.edEmail.error?.length ?: 0 > 0) {
                binding.edEmail.requestFocus()
            } else if (binding.edPassword.error?.length ?: 0 > 0) {
                binding.edPassword.requestFocus()
            } else {
                val email = binding.edEmail.text.toString()
                val password = binding.edPassword.text.toString()
                viewModel.getLogin(email, password)
            }
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
                viewModel.login.observe(viewLifecycleOwner) {
                    pref.apply {
                        setStringPreference(USER_ID, it.result.id)
                        setStringPreference(TOKEN, it.result.token)
                        setStringPreference(NAME, it.result.name)
                        setStringPreference(EMAIL, viewModel.tempEmail.value.toString())
                        setBooleanPreference(IS_LOGIN, true)
                    }
                    if (it.result.token.isNotEmpty()) {
                        requireActivity().run {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        Toast.makeText(
                            activity,
                            resources.getString(R.string.welcome) + it.result.name,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                viewModel.error.observe(viewLifecycleOwner) {
                    it?.let {
                        if (it.isNotEmpty()) {
                            Toast.makeText(
                                activity,
                                resources.getString(R.string.failed) + it,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(
                    R.id.frame_container,
                    RegisterFragment(),
                    RegisterFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun startAnimation() {
        val welcome = ObjectAnimator.ofFloat(binding.labelGreet, View.ALPHA, 1f).setDuration(500)
        val appName = ObjectAnimator.ofFloat(binding.labelAppName, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.labelAuth, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.edEmail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.edPassword, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnAction, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.registerContainer, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(welcome, appName, login, email, password, btnLogin, register)
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