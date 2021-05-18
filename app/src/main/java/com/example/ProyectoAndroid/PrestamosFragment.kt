package com.example.ProyectoAndroid

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PrestamosFragment : Fragment() {

    lateinit var miRecyclerView: RecyclerView
    lateinit var mimenu : Menu

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_prestamos, container, false)

        var listaItems: List<MyItemEntity> = listOf()
        (activity as MainActivity).miViewModel.listaMyItem.observe(activity as MainActivity){listaItems->
            listaItems?.let {
                miRecyclerView=rootView.findViewById(R.id.recyclerView)
                miRecyclerView.layoutManager= LinearLayoutManager(activity)
                miRecyclerView.adapter=MyItemAdapter(
                    it,
                    activity as MainActivity)
            }
        }

        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        activity?.setTitle("Prestamos")

        view.findViewById<Button>(R.id.bt_volver).setOnClickListener {
            findNavController().navigate(R.id.action_PrestamosFragment_to_Welcome)
        }

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_PrestamosFragment_to_addPrestamoFragment)
        }
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        mimenu = menu
        menu.findItem(R.id.action_add)?.isVisible=true
        menu.findItem(R.id.action_search)?.isVisible=true
        menu.findItem(R.id.action_delete)?.isVisible=false
        menu.findItem(R.id.action_save)?.isVisible=false

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_add->findNavController().navigate(R.id.action_PrestamosFragment_to_addPrestamoFragment)
        }
        return super.onOptionsItemSelected(item)
    }



}