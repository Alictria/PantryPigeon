package com.example.pantrypigeon.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "product_information")
data class Product(
    @ColumnInfo(name = "product_name") val productName: String,
    @ColumnInfo(name = "expiration_date") val expirationDate: Date = Date(),
    @ColumnInfo(name = "storage_location") val storageLocation: String,
    @ColumnInfo(
        name = "product_image",
        typeAffinity = ColumnInfo.BLOB
    ) val productImage: ByteArray? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)