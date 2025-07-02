package com.example.laboralex.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.laboralex.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val APP_STATE = "app_state"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "laboralex.db")
            .createFromAsset("laboralex_default.db").build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase) = db.UserDao()

    @Provides
    @Singleton
    fun providesSkillDao(db: AppDatabase) = db.SkillDao()

    @Provides
    @Singleton
    fun providesCompanyDao(db: AppDatabase) = db.CompanyDao()

    @Provides
    @Singleton
    fun providesCompanySkillDao(db: AppDatabase) = db.CompanySkillDao()

    @Provides
    @Singleton
    fun providesCourseDao(db: AppDatabase) = db.CourseDao()

    @Provides
    @Singleton
    fun providesCourseSkillDao(db: AppDatabase) = db.CourseSkillDao()

    @Provides
    @Singleton
    fun providesAppStateDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(APP_STATE) }
        )
    }
}