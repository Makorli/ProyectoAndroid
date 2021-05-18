package com.example.ProyectoAndroid


import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class VM(private val repositorio: Repositorio) : ViewModel() {

    var listaMyItem: LiveData<List<MyItemEntity>> = repositorio.listaMyItem.asLiveData()
    lateinit var miItem: LiveData<MyItemEntity>

    fun addItem(item: MyItemEntity) = viewModelScope.launch {
        repositorio.insert(item)
    }

    fun replaceItem(item: MyItemEntity) = viewModelScope.launch {
        repositorio.update(item)
    }

    fun deleteItem(item: MyItemEntity) = viewModelScope.launch {
        repositorio.delete(item)
    }

    fun loadId(id: Int) = viewModelScope.launch {
        miItem = repositorio.loadId(id).asLiveData()
    }


    //fun existsItem(item: MyItemEntity): Boolean = item in this.listaMyItem.value

    fun validarDatosItem(item: String, persona: String, fecha: String, estado: Boolean): Boolean {
        return !((item == "") || (persona == "") || (fecha == ""))
    }

    fun getCurrentDateToString(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())

        /*
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        tvFecha.text = String.format(calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" + (calendar.get(
            Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR))
         */
    }

    fun convertStringToData(getDate: String): Date? {
        var today: Date? = null
        val simpleDate = SimpleDateFormat("dd/MM/yyyy")
        try {
            today = simpleDate.parse(getDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return today
    }

}


class ItemsViewModelFactory(private val repositorio: Repositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VM(repositorio) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}