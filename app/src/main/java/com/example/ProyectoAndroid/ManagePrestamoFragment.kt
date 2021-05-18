package com.example.ProyectoAndroid

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class ManagePrestamoFragment : Fragment() {

    lateinit var tvItem: EditText
    lateinit var tvPersona: EditText
    lateinit var tvFecha: TextView
    lateinit var tvEstado: TextView

    lateinit var btGuardar: Button
    lateinit var btSalir: Button
    lateinit var btBorrar: Button

    lateinit var myItem: MyItemEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_prestamo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvItem = view.findViewById(R.id.lt_manageItem_item)
        tvPersona = view.findViewById(R.id.lt_manageItem_Persona)
        tvFecha = view.findViewById(R.id.lt_manageItem_tv_Fecha)
        tvEstado = view.findViewById(R.id.lt_manageItem_tv_Estado)

        btGuardar = view.findViewById(R.id.lt_manageItem_bt_Guardar)
        btSalir = view.findViewById(R.id.lt_manageItem_bt_salir)
        btBorrar = view.findViewById(R.id.lt_manageItem_bt_delete)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        activity?.setTitle("Gestion")

        val id: Int = arguments?.getInt("id") ?: -1

        (activity as MainActivity).miViewModel.loadId(id)
        (activity as MainActivity).miViewModel.miItem.observe(activity as MainActivity) {
            it?.let {

                myItem = it

                tvItem.setText(it.nombre)
                tvPersona.setText(it.persona)
                tvFecha.setText(it.fecha)
                tvEstado.setText(visualizarEstado(it.retornado))

            }
        }

        tvFecha.setOnClickListener { getfecha() }

        tvEstado.setOnClickListener {
            var currentstatus = estadoActual(tvEstado.text.toString())
            currentstatus = !currentstatus
            tvEstado.text=visualizarEstado(currentstatus)
        }

        btGuardar.setOnClickListener{ guardar() }

        btBorrar.setOnClickListener{ borrar() }

        btSalir.setOnClickListener {
            findNavController().navigate(R.id.action_managePrestamoFragment_to_PrestamosFragment)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_add)?.isVisible = false
        menu.findItem(R.id.action_search)?.isVisible = false
        menu.findItem(R.id.action_delete)?.isVisible = true
        menu.findItem(R.id.action_save)?.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> { guardar() }
            R.id.action_delete -> { borrar() }
        }
        return super.onOptionsItemSelected(item)
    }


    fun visualizarEstado(estado: Boolean):String{
        return if (estado) "RETORNADO" else "EN PRESTAMO"
    }
    fun estadoActual(estado:String):Boolean{
        return estado == "RETORNADO"
    }

    fun getfecha(){
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

    fun guardar(){
        val item = tvItem.text.toString() ?: ""
        val persona = tvPersona.text.toString() ?: ""
        val fecha = tvFecha.text.toString() ?: ""
        val estado = tvEstado.text.toString()=="RETORNADO"


        if ((activity as MainActivity).miViewModel.validarDatosItem(item, persona, fecha, estado)) {

            (activity as MainActivity)
                .miViewModel
                .replaceItem(
                    MyItemEntity(
                        id = myItem.id,
                        nombre = item,
                        persona = persona,
                        fecha = fecha,
                        retornado = estado
                    )
                )

            Toast.makeText(activity, "Prestamo modificado con exito", Toast.LENGTH_SHORT)
                .show()

            (activity as MainActivity)
                .findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_managePrestamoFragment_to_PrestamosFragment)

        }
    }

    fun borrar(){

        var siono =false

        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        alertDialog.setPositiveButton("BORRAR") { _, _ ->
            (activity as MainActivity).miViewModel.deleteItem(myItem)

            Toast.makeText(requireActivity(), "Prestamo borrado", Toast.LENGTH_SHORT).show()

            (activity as MainActivity)
                .findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_managePrestamoFragment_to_PrestamosFragment)

        }
        alertDialog.setNegativeButton("CANCELAR") { _, _ ->
            Toast.makeText(requireActivity(), "Cancelando..", Toast.LENGTH_SHORT).show()
        }

        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()

    }

}

