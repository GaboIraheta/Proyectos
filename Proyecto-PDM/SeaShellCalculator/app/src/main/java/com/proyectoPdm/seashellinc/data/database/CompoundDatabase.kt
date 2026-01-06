package com.proyectoPdm.seashellinc.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.proyectoPdm.seashellinc.data.database.daos.CompoundDao
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity

@Database(entities = [CompoundEntity::class], version = 1)
abstract class CompoundDatabase : RoomDatabase() {
    abstract fun CompoundDao() : CompoundDao
}