package com.example.pantrypigeon.addProduct

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import com.example.pantrypigeon.ProductEvent
import com.example.pantrypigeon.ProductState
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductView(
    state: ProductState,
    onEvent: (ProductEvent) -> Unit,
    navBack: () -> Unit,
) {
    val openDialog = remember { mutableStateOf(false) }
    var expirationDate by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { run { onEvent(ProductEvent.SaveProduct); navBack() }; },
            ) {
                Icon(Icons.Filled.Check, "Save item")
            }
        },
    ) { padding ->

        val maxNameLength = 50

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Add Product")
            OutlinedTextField(
                modifier = Modifier,
                value = state.productName,
                label = { Text("Product name") },
                onValueChange = {
                    if (it.length <= maxNameLength) {
                        onEvent(ProductEvent.SetProductName(it))
                    }
                },
            )

            OutlinedTextField(
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            openDialog.value = true
                        }
                    },
                value = expirationDate,
                onValueChange = {},
                label = { Text("Expiration date") }
            )

            var expanded by remember { mutableStateOf(false) }
            var pantryType by remember { mutableStateOf(PantryType.FRIDGE) }
            ExposedDropdownMenuBox(
                modifier = Modifier,
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    value = pantryType.description,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor(),
                    label = { Text(text = "Pantry Type") }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    PantryType.entries.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.description) },
                            onClick = {
                                onEvent(ProductEvent.SetStorageLocation(item))
                                pantryType = item
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
    if (openDialog.value) {
        val datePickerState = rememberDatePickerState()
        val dateInMilli = datePickerState.selectedDateMillis

        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false

                        val simpleDateFormat = SimpleDateFormat("dd.MM.yy")

                        if (dateInMilli != null) {
                            expirationDate =
                                simpleDateFormat.format(Date(dateInMilli))
                            onEvent(ProductEvent.SetExpirationDate(Date(dateInMilli)))
                        }
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}