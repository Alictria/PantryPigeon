package com.example.pantrypigeon.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import java.text.SimpleDateFormat


@Composable
fun HomeView(
    onClickAddProduct: () -> Unit,
    navPantry: () -> Unit,
    state: ProductState
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onClickAddProduct() }
            ) {
                Icon(Icons.Filled.Add, "Add product")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Expires next!", modifier = Modifier.padding(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, RoundedCornerShape(10))
                    .height(400.dp)
            ) {
                items(state.products) { product ->
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

                        Text(text = product.productName/* modifier = Modifier.padding(16.dp)*/)
                        Text(text = formattedDate)
                        Text(text = product.storageLocation)
                    }
                }
            }
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.End,
            ) {
                ExtendedFloatingActionButton(
                    onClick = { navPantry() },
                    icon = { Icon(Icons.Filled.List, "Add item.") },
                    text = { Text(text = "View Pantry") },
                )
            }
        }
    }
}
