package com.example.practica_transaccionesconsqlitedyland.modelo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.practica_transaccionesconsqlitedyland.dao.AppDatabase
import com.example.practica_transaccionesconsqlitedyland.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {


    private val userDao: UserDao

    init {
        val database = AppDatabase.getInstance(application.applicationContext)
        userDao = database.UserDao()
    }


    suspend fun insertar(user: User) = withContext(Dispatchers.IO){
        userDao.addUser(user)

    }

    suspend fun actualizar(user: User) = withContext(Dispatchers.IO){
        userDao.updateUser(user)

    }

    suspend fun eliminar(user: User) = withContext(Dispatchers.IO){
        userDao.deleteUser(user)
    }

    val todos: LiveData<List<User>> = userDao.getAllUser()
}