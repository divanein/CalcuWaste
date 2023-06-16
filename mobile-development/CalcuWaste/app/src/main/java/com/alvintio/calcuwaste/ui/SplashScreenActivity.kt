package com.alvintio.calcuwaste.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvintio.calcuwaste.utils.Preference
import com.alvintio.calcuwaste.R
import com.alvintio.calcuwaste.databinding.ActivitySplashScreenBinding
import com.alvintio.calcuwaste.ui.auth.LoginFragment
import java.util.Timer
import kotlin.concurrent.schedule

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var pref: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        pref = Preference(this)

        if (pref.token != "") Timer().schedule(2000) {
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        } else Timer().schedule(2000) {
            val fragmentManager = supportFragmentManager
            val loginFragment = LoginFragment()
            val fragment = fragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName)

            if (fragment !is LoginFragment) {
                fragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.frame_container,
                        loginFragment,
                        LoginFragment::class.java.simpleName
                    )
                    .commit()
            }
        }
    }
}