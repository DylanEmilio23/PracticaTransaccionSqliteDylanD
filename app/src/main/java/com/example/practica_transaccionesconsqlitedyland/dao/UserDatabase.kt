package com.example.practica_transaccionesconsqlitedyland.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practica_transaccionesconsqlitedyland.modelo.User


@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}