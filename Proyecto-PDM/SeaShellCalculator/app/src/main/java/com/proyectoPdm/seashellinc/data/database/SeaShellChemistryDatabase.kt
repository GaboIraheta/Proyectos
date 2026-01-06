package com.proyectoPdm.seashellinc.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.proyectoPdm.seashellinc.data.database.daos.CompoundDao
import com.proyectoPdm.seashellinc.data.database.daos.UserDao
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.database.entity.UserEntity

@Database(entities = [CompoundEntity::class, UserEntity::class], version = 2, exportSchema = true)
abstract class SeaShellChemistryDatabase : RoomDatabase() {
    abstract fun CompoundDao() : CompoundDao
    abstract fun UserDao() : UserDao
}