package com.example.laboralex.di

import android.content.Context
import androidx.room.Room
import com.example.laboralex.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "laboralex.db").build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase) = db.UserDao()

    @Provides
    @Singleton
    fun providesSpecialityDao(db: AppDatabase) = db.SpecialityDao()

    @Provides
    @Singleton
    fun providesCompanyDao(db: AppDatabase) = db.CompanyDao()
}