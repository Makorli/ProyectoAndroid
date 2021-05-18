package com.example.ProyectoAndroid

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "table_items")
data class MyItemEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @NonNull @ColumnInfo(name = "nombre") val nombre: String = "",
    @NonNull @ColumnInfo(name = "persona") val persona: String = "",
    @NonNull @ColumnInfo(name = "fecha") val fecha: String = "",
    @NonNull @ColumnInfo(name = "retornado") var retornado:Boolean =false

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MyItemEntity

        if (nombre != other.nombre) return false
        if (persona != other.persona) return false
        if (fecha != other.fecha) return false
        if (retornado != other.retornado) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nombre.hashCode()
        result = 31 * result + persona.hashCode()
        result = 31 * result + fecha.hashCode()
        result = 31 * result + retornado.hashCode()
        return result
    }
}
