package com.example.ProyectoAndroid

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WelcomeFragment : Fragment() {

    lateinit var tv_usuario: TextView
    lateinit var tv_pwd: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        tv_usuario = view.findViewById(R.id.et_usuario)
        tv_pwd = view.findViewById(R.id.et_password)

        view.findViewById<Button>(R.id.bt_login).setOnClickListener {

            val usuario = tv_usuario.text.toString()
            val pwd = tv_pwd.text.toString()

            tv_usuario.text=""
            tv_pwd.text = ""

            if (usuario == "" || pwd == "")
                Toast.makeText(
                    activity,
                    "Datos no validos.Verifique por favor.",
                    Toast.LENGTH_SHORT
                ).show()

            val cred: SharedPreferences =
                (activity as MainActivity).getSharedPreferences("usuario", Context.MODE_PRIVATE)
            val savedUser = cred.getString("user", null)
            val savedPwd = cred.getString("user", null)

            if (savedUser == null)
                Toast.makeText(activity, "Primero debes realizar el registro", Toast.LENGTH_LONG)
                    .show()
            else if ((savedUser == usuario) && (savedPwd == pwd))
                findNavController().navigate(R.id.action_Welcome_to_PrestamosFragment)
            else
                Toast.makeText(activity, "Credenciales no válidas.", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<Button>(R.id.bt_signin).setOnClickListener {
            val usuario = tv_usuario.text.toString()
            val pwd = tv_pwd.text.toString()
            tv_usuario.text=""
            tv_pwd.text = ""

            if (usuario == "" || pwd == "")
                Toast.makeText(
                    activity,
                    "Datos no validos.Verifique por favor.",
                    Toast.LENGTH_SHORT
                ).show()
            else{
                val cred: SharedPreferences =
                    (activity as MainActivity).getSharedPreferences("usuario", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = cred.edit()
                editor.putString("user", usuario)
                editor.putString("password", pwd)
                editor.apply()
                Toast.makeText(activity, "Registro de usuario realizado", Toast.LENGTH_SHORT).show()
            }
        }

    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_add)?.isVisible=false
        menu.findItem(R.id.action_search)?.isVisible=false
        menu.findItem(R.id.action_delete)?.isVisible=false
        menu.findItem(R.id.action_save)?.isVisible=false

    }
}