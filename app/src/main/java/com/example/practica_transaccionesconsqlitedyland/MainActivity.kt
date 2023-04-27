package com.example.practica_transaccionesconsqlitedyland

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_transaccionesconsqlitedyland.dao.UserAdapter

import com.example.practica_transaccionesconsqlitedyland.databinding.ActivityMainBinding
import com.example.practica_transaccionesconsqlitedyland.modelo.User
import com.example.practica_transaccionesconsqlitedyland.modelo.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private var UserSeleccionado: User? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter { user ->
            UserSeleccionado = user
            binding.etFirstname.setText(user.firstName)
            binding.etLastname.setText(user.lastName)
            binding.etCarrera.setText(user.carrera)
            binding.etAnio.setText(user.año.toString()) }


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }

        viewModel.todos.observe(this, Observer { user ->
            userAdapter.setUser(user)
        })

        binding.btnLimpiar.setOnClickListener {
            try {
                limpiarCampos()
            } catch (ex: Exception) {
                Toast.makeText(
                    this@MainActivity, "Error : ${ex.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnInsertar.setOnClickListener {
            try {
                if (validarCampos()) {
                    val nombres = binding.etFirstname.text.toString()
                    val apellidos = binding.etLastname.text.toString()
                    val carrera = binding.etCarrera.text.toString()
                    val anio = binding.etAnio.text.toString().toIntOrNull() ?: 0

                    val user = User(
                        firstName = nombres, lastName = apellidos, carrera = carrera, año = anio
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.insertar(user)
                        limpiarCampos()
                    }
                }
            } catch (ex: Exception) {
                Toast.makeText(
                    this@MainActivity, "Error : ${ex.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnEditar.setOnClickListener {
            try {
                if (validarCampos()) {
                    UserSeleccionado?.let { User ->
                        val nombres = binding.etFirstname.text.toString()
                        val apellidos = binding.etFirstname.text.toString()
                        val carrera = binding.etCarrera.text.toString()
                        val anio = binding.etAnio.text.toString().toIntOrNull() ?: 0

                        val userActualizado = User(
                            id = User.id,
                            firstName = nombres,
                            lastName = apellidos,
                            carrera = carrera,
                            año = anio
                        )
                        limpiarCampos()
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.actualizar(userActualizado)
                        }
                    }
                }
            } catch (ex: Exception) {
                Toast.makeText(
                    this@MainActivity, "Error : ${ex.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnEliminar.setOnClickListener {
            try {
                if (validarCampos()) {
                    AlertDialog.Builder(this).setTitle("Confirmación")
                        .setMessage("¿Estás seguro de eliminar este registro?")
                        .setPositiveButton("Sí") { _, _ ->
                            UserSeleccionado?.let { User ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.eliminar(User)
                                    limpiarCampos()
                                }
                            }
                        }.setNegativeButton("No", null).show()
                }
            } catch (ex: Exception) {
                Toast.makeText(
                    this@MainActivity, "Error : ${ex.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun limpiarCampos() {
        binding.etFirstname.text.clear()
        binding.etLastname.text.clear()
        binding.etCarrera.text.clear()
        binding.etAnio.text.clear()
    }

    private fun validarCampos(): Boolean {
        var valido = true

        val nombres = binding.etFirstname.text.toString()
        val apellidos = binding.etLastname.text.toString()
        val carrera = binding.etCarrera.text.toString()
        val anio = binding.etAnio.text.toString()

        if (nombres.isBlank()) {
            binding.etFirstname.setError("Este campo no puede estar vacío")
            valido = false
        }
        if (apellidos.isBlank()) {
            binding.etLastname.setError("Este campo no puede estar vacío")
            valido = false
        }

        if (carrera.isBlank()) {
            binding.etCarrera.setError("Este campo no puede estar vacío")
            valido = false
        }

        if (anio.isBlank()) {
            binding.etAnio.setError("Este campo no puede estar vacío")
            valido = false
        } else if (anio.toIntOrNull() == null) {
            binding.etAnio.setError("Introduzca un número válido")
            valido = false
        }

        return valido
    }
}


