package com.alvintio.calcuwaste.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvintio.calcuwaste.adapter.NewsAdapter
import com.alvintio.calcuwaste.api.response.NewsItem
import com.alvintio.calcuwaste.databinding.FragmentHomeBinding
import com.alvintio.calcuwaste.utils.Preference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: Preference
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        pref = Preference(requireActivity())
        val token = pref.token.toString()

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.listNews.layoutManager = layoutManager

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getListStory("Bearer $token").observe(requireActivity()) {
                setStory(it)
            }

            viewModel.isLoading.observe(requireActivity()) {
                showLoading(it)
            }
        }
    }

    private fun setStory(storyList: List<NewsItem>) {
        val adapter = NewsAdapter(storyList)
        binding.listNews.adapter = adapter
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