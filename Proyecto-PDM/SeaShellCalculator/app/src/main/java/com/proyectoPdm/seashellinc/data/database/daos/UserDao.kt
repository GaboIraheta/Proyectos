package com.proyectoPdm.seashellinc.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.proyectoPdm.seashellinc.data.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    //permite agregar un nuevo usuario por registro
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUserToDatabase(user : UserEntity)

    //permite actualizar campos de un usuario
    @Update
    suspend fun updateUser(user: UserEntity)

    //permite obtener un usuario por su ID despues del login
    @Query("SELECT * FROM users WHERE _id = :userId")
    fun getUserById(userId : String) : Flow<UserEntity>

    //permite obtener un usuario por su email
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserForLogin(email : String) : UserEntity?

    //permite eliminar un usuario
    @Delete
    suspend fun deleteUser(user : UserEntity)

    //permite eliminar un usuario por su ID
    @Query("DELETE FROM users WHERE _id = :userId")
    suspend fun deleteUserById(userId : String)

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getLoggedUser() : UserEntity?

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}