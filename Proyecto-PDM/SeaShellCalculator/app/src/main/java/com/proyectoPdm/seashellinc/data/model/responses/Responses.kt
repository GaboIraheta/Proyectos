package com.proyectoPdm.seashellinc.data.model.responses

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.database.entity.UserEntity
import com.proyectoPdm.seashellinc.data.model.compound.Compound
import com.proyectoPdm.seashellinc.data.model.user.User
import kotlin.toString

data class MolarMassData (
    @SerializedName("_id")
    val id : String?,
    val name : String,
    val unit : String,
    val value : Double
) {
    fun toMolarMassEntity(userId : String) : CompoundEntity {
        return CompoundEntity(
            id = id.toString(),
            compound = Compound(name, unit, value),
            userId = userId,
        )
    }
}

data class UserObjectResponse (
    @SerializedName("user")
    val user : UserData,
    val token : String
)

data class UserData (
    @SerializedName("_id")
    val id : String,
    val username : String,
    val email : String,
    val isPremium : Boolean,
    val molarMassList : List<MolarMassData>
) {
    fun toUserEntity(token : String) : UserEntity {
        return UserEntity(
            id = id,
            user = User(username, email, isPremium),
            token = token
        )
    }

    fun toMolarMassEntity() : List<CompoundEntity> {
//        Log.d("MapMolarMassList", molarMassList.toString())
        return molarMassList.map { molarMass ->
            Log.d("MapMolarMassList", molarMass.toString())
            Log.d("MapMolarMassList", id.toString())
            Log.d("MapMolarMassList", molarMass.id.toString())
            CompoundEntity(
                id = molarMass.id.toString(),
                compound = Compound(molarMass.name, molarMass.unit, molarMass.value),
                userId = id
            )
        }
    }
}