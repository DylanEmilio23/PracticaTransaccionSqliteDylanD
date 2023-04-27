package com.example.practica_transaccionesconsqlitedyland.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.practica_transaccionesconsqlitedyland.modelo.User

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user ORDER BY id DESC")
    fun getAllUser(): LiveData<List<User>>

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

}