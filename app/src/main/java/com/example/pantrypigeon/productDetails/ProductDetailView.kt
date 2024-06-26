package com.example.pantrypigeon.productDetails

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pantrypigeon.ProductEvent
import com.example.pantrypigeon.data.database.DatabaseProduct

@Composable
fun ProductDetailView(
    stateProductDetails: DatabaseProduct?,
    onEvent: (ProductEvent) -> Unit,
    naveToPantry: () -> Unit,
    navToRecipeSuggestion: (product: String) -> Unit
) {
    val brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue))
    if (stateProductDetails != null) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(text = "Product details", modifier = Modifier.padding(16.dp))

            Text(
                text = stateProductDetails.productName, modifier = Modifier
                    .border(2.dp, brush, RoundedCornerShape(20))
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Row(
                modifier = Modifier
                    .border(2.dp, brush, RoundedCornerShape(20))
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //TODO load date from stateProductDetails in right time format
                Text(text = stateProductDetails.expirationDate.toString())
                Text(text = stateProductDetails.storageLocation.description)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "text",
                    Modifier
                        .padding(16.dp)
                        .clickable { navToRecipeSuggestion(stateProductDetails.productName) }
                )

                FloatingActionButton(
                    onClick = { run { onEvent(ProductEvent.DeleteProduct(stateProductDetails)); naveToPantry() } },
                    modifier = Modifier.padding(16.dp),
                    containerColor = Color.Red
                ) {
                    Icon(Icons.Filled.Delete, "Delete item button")
                }
            }

        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No Product with this id", modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}
