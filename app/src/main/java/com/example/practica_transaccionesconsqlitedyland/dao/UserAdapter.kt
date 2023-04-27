package com.example.practica_transaccionesconsqlitedyland.dao

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_transaccionesconsqlitedyland.modelo.User
import com.example.practica_transaccionesconsqlitedyland.databinding.EstudianteItemBinding


class UserAdapter(private val onItemClick: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var User = listOf<User>()

   fun setUser(user: List<User>) {
        this.User = user
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = EstudianteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(User[position])
    }

    override fun getItemCount(): Int = User.size

    inner class UserViewHolder(private val binding: EstudianteItemBinding, private val onItemClick: (User) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(User: User) {
            binding.tvNombreCompleto.text = "${User.firstName} ${User.lastName}"
            binding.tvCarrera.text = User.carrera
            binding.tvAnio.text = User.año.toString()

            binding.root.setOnClickListener { onItemClick(User) }
        }
    }




}

