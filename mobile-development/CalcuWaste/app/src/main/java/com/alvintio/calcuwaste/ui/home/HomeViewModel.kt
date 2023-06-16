package com.alvintio.calcuwaste.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvintio.calcuwaste.api.ApiConfig
import com.alvintio.calcuwaste.api.response.NewsItem
import com.alvintio.calcuwaste.api.response.NewsResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getListStory(token: String): LiveData<List<NewsItem>> {
        _isLoading.value = true
        val story = MutableLiveData<List<NewsItem>>()
        viewModelScope.launch {
            val client = ApiConfig.getApiService().getAllNews(token)
            client.enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>,
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        story.value = response.body()?.news
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        }
        return story
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}