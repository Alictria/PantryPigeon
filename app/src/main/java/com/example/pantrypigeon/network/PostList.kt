package com.example.pantrypigeon.network

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import com.example.pantrypigeon.MainViewModel


@Composable
fun PostList(viewModel: MainViewModel, productName: String) {
    val posts by viewModel.posts
    LazyColumn {
        items(posts) { post ->
            Text(text = post.strMeal)
        }
    }
    DisposableEffect(Unit) {
        viewModel.getPosts(productName)
        onDispose {}
    }
}