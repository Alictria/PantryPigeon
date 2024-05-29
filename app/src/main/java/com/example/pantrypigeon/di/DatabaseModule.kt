package com.example.pantrypigeon.di

import android.content.Context
import androidx.room.Room
import com.example.pantrypigeon.database.ProductDao
import com.example.pantrypigeon.database.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(productDatabase: ProductDatabase): ProductDao {
        return productDatabase.dao
    }

    @Provides
    @Singleton
    fun provideProductDatabase(@ApplicationContext appContext: Context): ProductDatabase {
        return Room.databaseBuilder(
            appContext,
            ProductDatabase::class.java,
            "products.db"
        ).build()
    }
}