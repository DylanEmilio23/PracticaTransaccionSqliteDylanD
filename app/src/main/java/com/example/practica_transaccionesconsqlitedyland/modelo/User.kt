package com.example.practica_transaccionesconsqlitedyland.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var carrera: String = "",
    var año: Int
)
