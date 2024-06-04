package com.example.pantrypigeon

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypigeon.network.Post
import com.example.pantrypigeon.network.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    // TODO inject api with hilt in the future
) : ViewModel() {
    private val apiService = RetrofitInstance.api
    val posts: MutableState<List<Post>> = mutableStateOf(emptyList())
    fun getPosts(string: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getPosts(string)
                posts.value = response.meals
            } catch (e: Exception) {
                Log.e("MainViewModel", "Api request error", e)
            }
        }
    }
}