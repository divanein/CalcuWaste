package com.alvintio.calcuwaste.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.alvintio.calcuwaste.utils.Preference
import com.alvintio.calcuwaste.R
import com.alvintio.calcuwaste.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: Preference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        pref = Preference(requireActivity())

        binding.username.text = pref.name.toString()
        binding.email.text = pref.email.toString()

        val btnLogout: Button = binding.btnLogout
        btnLogout.setOnClickListener(this)

        val btnLanguage: RelativeLayout = binding.btnLanguage
        btnLanguage.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_logout) {
            pref.clearPreference()
            (activity as MainActivity).routeToMain()
        }
        if (v.id == R.id.btn_language) {
            (activity as MainActivity).changeLanguage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}