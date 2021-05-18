package com.example.ProyectoAndroid

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


class AddPrestamoFragment : Fragment() {

    lateinit var tvGetDate: TextView

    lateinit var tvItem: TextView
    lateinit var tvPersona: TextView
    lateinit var tvFecha: TextView
    lateinit var btGetDate: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_addprestamo, container, false)
        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        activity?.setTitle("Añadir")

        lateinit var myItem: MyItemEntity

        val id: Int = arguments?.getInt("id") ?: -1

        tvItem = view.findViewById<TextView>(R.id.lt_addPrestamo_item)
        tvPersona = view.findViewById<TextView>(R.id.lt_addPersona_Persona)
        tvFecha = view.findViewById<TextView>(R.id.lt_addPersona_tv_Fecha)
        btGetDate = view.findViewById<Button>(R.id.lt_addPersona_bt_GetFecha)

        val btAdd = view.findViewById<Button>(R.id.lt_addPersona_bt_Guardar)
        //insertamos la fecha de hoy en el tvFecha
        tvFecha.text = (activity as MainActivity).miViewModel.getCurrentDateToString()

        /*
              val btEdit = view.findViewById<Button>(R.id.fr_datos_bt_edit)
              val btDelete = view.findViewById<Button>(R.id.fr_datos_bt_delete)



              if (id == -1) {
                  btAdd.isEnabled = true; btDelete.isEnabled = false; btEdit.isEnabled = false

                  tvItem.hint = getString(R.string.Nombre); tvItem.text = ""
                  tvTipo.hint = getString(R.string.Tipo); tvTipo.text = ""
                  tvPersona.hint = getString(R.string.Persona); tvPersona.text = ""

              } else {
                  btAdd.isEnabled = false; btDelete.isEnabled = true; btEdit.isEnabled = true

                  (activity as MainActivity).miViewModel.loadId(id)
                  (activity as MainActivity).miViewModel.miItem.observe(activity as MainActivity) {
                      it?.let {

                          myItem = it

                          tvItem.setText(it.nombre)
                          tvTipo.setText(it.tipo)
                          tvPersona.setText(it.persona.toString())
                      }
                  }
              }

         */

        btGetDate.setOnClickListener {
            // Create the date picker builder and set the title
            val builder = MaterialDatePicker.Builder.datePicker()
            // create the date picker
            val datePicker = builder.build()
            // set listener when date is selected
            datePicker.addOnPositiveButtonClickListener {
                // Create calendar object and set the date to be that returned from selection
                val micalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                micalendar.time = Date(it)
                tvFecha.text = String.format(
                    micalendar.get(Calendar.DAY_OF_MONTH).toString() + "/" + (micalendar.get(
                        Calendar.MONTH
                    ) + 1) + "/" + micalendar.get(Calendar.YEAR)
                )
            }
            datePicker.show(getParentFragmentManager(), "MyTAG")
        }


        btAdd.setOnClickListener {
            guardar()
        }

        /*
        btEdit.setOnClickListener {
            val nombre = tvItem.text.toString() ?: ""
            val tipo = tvTipo.text.toString() ?: ""
            val persona = tvPersona.text.toString() ?: ""


            if ((activity as MainActivity).miViewModel.validarDatosItem(nombre, tipo, persona)) {

                (activity as MainActivity)
                    .miViewModel
                    .replaceItem(
                        MyItemEntity(
                            id = myItem.id,
                            nombre = nombre,
                            tipo = tipo,
                            persona = persona
                        )
                    )

                Toast.makeText(activity, "Prestamo modificado con exito", Toast.LENGTH_SHORT)
                    .show()

                (activity as MainActivity)
                    .findNavController(R.id.nav_host_fragment)
                    .navigate(R.id.action_addPrestamoFragment_to_PrestamosFragment)

            }
        }

        btDelete.setOnClickListener {
            (activity as MainActivity).miViewModel.deleteItem(myItem)
            (activity as MainActivity)
                .findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_addPrestamoFragment_to_PrestamosFragment)
        }

         */

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_add)?.isVisible = false
        menu.findItem(R.id.action_search)?.isVisible = false
        menu.findItem(R.id.action_delete)?.isVisible = false
        menu.findItem(R.id.action_save)?.isVisible = true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> guardar()
        }
        return super.onOptionsItemSelected(item)
    }

    fun guardar() {

        val item = tvItem.text.toString()
        val persona = tvPersona.text.toString()
        val fecha = tvFecha.text.toString()

        if ((activity as MainActivity).miViewModel.validarDatosItem(
                item = item,
                persona = persona,
                fecha = fecha,
                false)) {

            (activity as MainActivity)
                .miViewModel
                .addItem(
                    MyItemEntity(
                        nombre = item,
                        persona = persona,
                        fecha = fecha,
                        retornado = false
                    )
                )

            Toast.makeText(activity, "Item añadido con exito", Toast.LENGTH_SHORT)
                .show()

            (activity as MainActivity)
                .findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_addPrestamoFragment_to_PrestamosFragment)
        } else {
            Toast.makeText(activity, "Rellene todos los campos", Toast.LENGTH_SHORT)
                .show()
        }

    }


}

