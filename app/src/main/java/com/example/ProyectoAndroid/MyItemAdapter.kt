package com.example.ProyectoAndroid


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class MyItemAdapter(var listaItems: List<MyItemEntity>, val actividad: Activity) :
    RecyclerView.Adapter<MyItemAdapter.ViewHolder>() {

    lateinit var bt_editar: Button
    lateinit var bt_retornar: Button

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnLongClickListener {
        var tv_nombre: TextView
        var tv_persona: TextView
        var tv_fecha: TextView

        var id: Int? = null

        init {

            v.setOnLongClickListener(this)

            tv_nombre = v.findViewById(R.id.ItemName)
            tv_persona = v.findViewById(R.id.ItemPersona)
            tv_fecha = v.findViewById(R.id.lt_addPersona_Fecha)

            bt_editar = v.findViewById<Button>(R.id.Item_bt_Editar)
            bt_retornar = v.findViewById(R.id.Item_bt_retornar)

            bt_editar.setOnClickListener {
                val bundle = bundleOf("id" to this.id)
                (actividad as MainActivity)
                    .findNavController(R.id.nav_host_fragment)
                    .navigate(R.id.action_PrestamosFragment_to_managePrestamoFragment, bundle)
            }

            bt_retornar.setOnClickListener {
                val pos: Int = this.adapterPosition
                val estado = listaItems[pos].retornado

                if (estado) {
                    Toast.makeText(actividad, "Edite elemento para volver a prestar", Toast.LENGTH_SHORT).show()
                } else {
                    listaItems[pos].retornado = !estado
                    (actividad as MainActivity).miViewModel.replaceItem(listaItems[pos])
                }
            }

        }

        override fun onLongClick(v: View?): Boolean {
            val bundle = bundleOf("id" to this.id)
            (actividad as MainActivity)
                .findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_PrestamosFragment_to_managePrestamoFragment, bundle)
            return true
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_myitem, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_nombre.text = listaItems[position].nombre
        holder.tv_persona.text = listaItems[position].persona
        holder.tv_fecha.text = listaItems[position].fecha

        if (listaItems[position].retornado == false) {
            bt_retornar.text = "Retornar"
        } else {
            bt_retornar.text = "Prestar"; }

        holder.id = listaItems[position].id
    }

    override fun getItemCount(): Int {
        return listaItems.count()
    }

}

