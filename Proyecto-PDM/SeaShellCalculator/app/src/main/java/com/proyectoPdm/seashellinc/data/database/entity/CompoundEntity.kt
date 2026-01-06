package com.proyectoPdm.seashellinc.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.proyectoPdm.seashellinc.data.model.compound.Compound
import java.util.UUID

@Entity(tableName = "compoundList")
class CompoundEntity (
    @PrimaryKey(autoGenerate = false) val id : String,
    @Embedded val compound : Compound,
    val userId : String
)
