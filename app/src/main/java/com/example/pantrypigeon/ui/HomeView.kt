package com.example.pantrypigeon.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pantrypigeon.ProductState
import androidx.compose.foundation.lazy.items
import java.text.SimpleDateFormat


@Composable
fun HomeView(
    onClickAddProduct: () -> Unit,
    navPantry: () -> Unit,
    state: ProductState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Expires next!", modifier = Modifier.padding(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black, RoundedCornerShape(10))
        ) {
            items(state.products) {product ->
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(20))
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val dateFormat = SimpleDateFormat("dd.MM.yy")
                    val formattedDate = dateFormat.format(product.expirationDate)

                    Text(text = product.productName,/* modifier = Modifier.padding(16.dp)*/)
                    Text(text = formattedDate)
                    Text(text = product.storageLocation)
                }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
    ) {


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            FloatingActionButton(
                onClick = { navPantry() },
                Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Home, "Add item")
            }
            FloatingActionButton(
                onClick = { onClickAddProduct() },
                Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, "Add item")
            }
        }
    }
}
