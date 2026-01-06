package com.proyectoPdm.seashellinc.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.proyectoPdm.seashellinc.data.database.entity.CompoundEntity
import com.proyectoPdm.seashellinc.data.model.compound.Compound
import kotlinx.coroutines.flow.Flow

@Dao
interface CompoundDao {

    //permite agregar un nuevo compuesto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCompound(compoundEntity: CompoundEntity)

    //permite agregar una nueva lista de compuestos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCompounds(compounds : List<CompoundEntity>)

    //permite agregar obtener la lista de masas molares del usuario
    @Query("SELECT * FROM compoundList WHERE userId = :userId")
    fun getMolarMassesForUser(userId : String) : List<CompoundEntity>

//    @Query("SELECT * FROM compoundList WHERE userId = :userId") NO USADA
//    fun getMolarMasses(userId : String) : Flow<List<CompoundEntity>>

    //permite obtener una masa molar por su ID
    @Query("SELECT * FROM compoundList WHERE id = :id")
    suspend fun getMolarMassById(id : String) : CompoundEntity? //NO USADA

    //permite obtener la lista de compuestos
    @Query("SELECT * FROM compoundList")
    fun getCompoundList(): Flow<List<CompoundEntity>>

//    //permite actualizar informacion de un compuesto por su ID
//    @Query("UPDATE compoundList SET compoundName = :compoundName, chemicalFormula = :chemicalFormula, molarMass = :molarMass WHERE id = :postId")
//    suspend fun updateCompound(
//        compoundName: String,
//        chemicalFormula: String,
//        molarMass: Double,
//        postId: String
//    ) NO USADA

    //    //permite eliminar un compuesto
    @Delete
    suspend fun deleteMolarMass(molarMas : CompoundEntity) //NO USADA

//    //permite eliminar un compuesto por el ID de la masa molar y del usuario
//    @Query("DELETE FROM compoundList WHERE id = :molarMassId AND userId = :userId")
//    suspend fun deleteMolarMassByIdAndUserId(molarMassId : String, userId : String) NO USADA

//    //permite eliminar un compuesto por el ID
//    @Query("DELETE FROM compoundList WHERE id = :compoundId")
//    suspend fun deleteCompoundById(compoundId: String) NO USADA

    //permrite eliminar todas las masas molares de un usuario
    @Query("DELETE FROM compoundList WHERE userId = :userId")
    suspend fun deleteAllMolarMassForUser(userId: String)
}