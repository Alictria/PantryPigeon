package com.example.pantrypigeon.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.example.pantrypigeon.MainActivity
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun AddProductScreen(context: Context, onClickSaveProduct: () -> Unit) {
    val sharedPrefKey = "pantryPigeon.preference.key"
    val sharedPref =
        context.getSharedPreferences("pantryPigeon.preference.key", Context.MODE_PRIVATE)
    var productName by remember { mutableStateOf("") }
    val maxNameLength = 50
    var expirationDate = ""
    var storageType = ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Add Product")

        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = productName,
            onValueChange = {
                if (it.length <= maxNameLength) {
                    productName = it
                }
            },
            label = { Text("Product name") }
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            expirationDate = ExpirationDatePicker()
            Spacer(modifier = Modifier.padding(4.dp))
            storageType = StorageLocationDropDown()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
    ) {
        FloatingActionButton(
            onClick = {
                with(sharedPref.edit()) {
                    val productEntries = setOf(productName, expirationDate, storageType)
                    putStringSet(productEntries.first(), productEntries)
                    apply()
                }

                onClickSaveProduct()
                // save input to sharedPrefs
                // clear all input fields
                //navigate back to home

            },
            Modifier.padding(16.dp)
        ) {
            Icon(Icons.Filled.Check, "Save item")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpirationDatePicker(
): String {
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
                        // TODO(Paty): Defocus from the text field
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
    OutlinedTextField(modifier = Modifier
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
    return expirationDate
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageLocationDropDown(): String {
    val pantryTypes = listOf("Fridge", "Freezer", "Pantry")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(pantryTypes[0]) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
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
                        selectedText = item
                        expanded = false
                    }
                )
            }
        }
    }
    return selectedText
}
