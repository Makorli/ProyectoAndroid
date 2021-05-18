package com.example.ProyectoAndroid

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MyItemDAO {

    //Insercion de un elemento
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MyItemEntity)

    //Borrado de un elemento
    @Delete
    suspend fun delete(item: MyItemEntity)

    //Actualizacion de un elemento
    @Update
    suspend fun update(item: MyItemEntity)

    //Lectura de todos los elementos
    @Query("SELECT * FROM table_items")
    fun loadAll(): Flow<List<MyItemEntity>>

    //Lectura de un elemento por filtrado por ID
    @Query("SELECT * FROM table_items WHERE id like :id")
    fun loadId(id: Int): Flow<MyItemEntity>

    // Lectura de elementos prestados filtrados por persona una persona
    @Query  ("SELECT * FROM TABLE_ITEMS WHERE persona like :persona")
    fun loadLoansOf(persona: String): Flow<MyItemEntity>

    // Lectura de elementos prestados filtrados por devolucion
    @Query  ("SELECT * FROM TABLE_ITEMS WHERE retornado=:rtdFilter")
    fun loadLoansReturned(rtdFilter:Boolean = false): Flow<MyItemEntity>

    // Lectura de elementos prestados filtrados por devolucion
    @Query  ("SELECT * FROM TABLE_ITEMS WHERE persona like :persona and retornado=:rtdFilter")
    fun loadLoansOfReturned(persona: String, rtdFilter:Boolean = false): Flow<MyItemEntity>

}