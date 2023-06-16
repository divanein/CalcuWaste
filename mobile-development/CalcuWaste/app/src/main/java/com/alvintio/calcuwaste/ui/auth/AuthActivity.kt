package com.alvintio.calcuwaste.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.alvintio.calcuwaste.ui.MainActivity
import com.alvintio.calcuwaste.utils.Preference
import com.alvintio.calcuwaste.R
import com.alvintio.calcuwaste.databinding.ActivityAuthBinding

@Suppress("DEPRECATION")
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var pref: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        pref = Preference(this)

        if (pref.token != "") {
            routeToMainActivity()
        } else {
            val fragmentManager = supportFragmentManager
            val loginFragment = LoginFragment()
            val fragment = fragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName)

            if (fragment !is LoginFragment) {
                fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, loginFragment, LoginFragment::class.java.simpleName)
                    .commit()
            }
        }
    }

    fun routeToMainActivity(){
        val intent = Intent(this@AuthActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}