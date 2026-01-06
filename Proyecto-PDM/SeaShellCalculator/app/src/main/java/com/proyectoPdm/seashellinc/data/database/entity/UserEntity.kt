package com.proyectoPdm.seashellinc.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.proyectoPdm.seashellinc.data.model.user.User

@Entity(tableName = "Users")
data class UserEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_id")
    val id : String,
    @Embedded val user : User,
    val token : String
)
