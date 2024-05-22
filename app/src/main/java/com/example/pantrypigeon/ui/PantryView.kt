package com.example.pantrypigeon.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pantrypigeon.ProductState
import java.text.SimpleDateFormat


@Composable
fun PantryView(
    state: ProductState,
    navProductDetailView:() -> Unit,
    getProductDetailsById:(id:Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Your Pantry")
        val brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue))

        LazyColumn {
            items(state.products) { product ->
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .border(2.dp, brush, RoundedCornerShape(20))
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable { navProductDetailView(); getProductDetailsById(product.id) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val dateFormat = SimpleDateFormat("dd.mm.yy")
                    val formattedDate = dateFormat.format(product.expirationDate)

                    Text(text = formattedDate)
                    Text(text = product.productName)
                    Text(text = product.storageLocation)
                }
            }
        }
    }
}
