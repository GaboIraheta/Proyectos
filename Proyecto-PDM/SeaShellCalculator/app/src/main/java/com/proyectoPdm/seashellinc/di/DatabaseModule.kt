package com.proyectoPdm.seashellinc.di

import android.content.Context
import androidx.room.Room
import com.proyectoPdm.seashellinc.data.database.SeaShellChemistryDatabase
import com.proyectoPdm.seashellinc.data.database.CompoundDatabase
import com.proyectoPdm.seashellinc.data.database.daos.CompoundDao
import com.proyectoPdm.seashellinc.data.database.daos.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context : Context
    ) : SeaShellChemistryDatabase {
        return Room.databaseBuilder(
            context,
            SeaShellChemistryDatabase::class.java,
            "SeaShellCalculatorDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providesUserDao(db : SeaShellChemistryDatabase) : UserDao {
        return db.UserDao()
    }

    @Provides
    fun providesCompoundDao(db : SeaShellChemistryDatabase) : CompoundDao {
        return db.CompoundDao()
    }
}