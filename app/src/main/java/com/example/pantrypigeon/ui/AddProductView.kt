package com.example.pantrypigeon.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import com.example.pantrypigeon.ProductEvent
import com.example.pantrypigeon.ProductState
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    state: ProductState,
    onEvent: (ProductEvent) -> Unit,
    navBack: () -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { run { onEvent(ProductEvent.SaveProduct); navBack() }; },
                Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Check, "Save item")
            }
        },
    ) { padding ->

        val maxNameLength = 50
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Add Product")
            OutlinedTextField(
                value = state.productName,
                onValueChange = {
                    if (it.length <= maxNameLength) {
                        onEvent(ProductEvent.SetProductName(it))
                    }
                },
                label = { Text("Product name") }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                var expirationDate by remember { mutableStateOf("") }
                val openDialog = remember { mutableStateOf(false) }
                if (openDialog.value) {
                    val datePickerState = rememberDatePickerState()

                    DatePickerDialog(
                        onDismissRequest = {
                            openDialog.value = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    openDialog.value = false

                                    val simpleDateFormat = SimpleDateFormat("dd.MM.yy")
                                    expirationDate =
                                        simpleDateFormat.format(Date(datePickerState.selectedDateMillis!!))
                                    // TODO(Paty): defocus from the text field
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
                OutlinedTextField(
                    modifier = Modifier
                        .width(100.dp)
                        .onFocusChanged {
                            if (it.isFocused) {
                                openDialog.value = true
                            }
                        },
                    value = expirationDate,
                    onValueChange = {
                    },
                    label = { Text("EXP") })

                val pantryTypes = listOf("Fridge", "Freezer", "Pantry")
                var expanded by remember { mutableStateOf(false) }
                var selectedText by remember { mutableStateOf(pantryTypes[0]) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    OutlinedTextField(
                        value = selectedText,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        pantryTypes.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    onEvent(ProductEvent.SetStorageLocation(item))
                                    selectedText = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}