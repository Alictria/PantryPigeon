package com.example.pantrypigeon.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [DatabaseProduct::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract val dao: ProductDao
}