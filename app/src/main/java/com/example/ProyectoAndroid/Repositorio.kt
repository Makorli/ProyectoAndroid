package com.example.ProyectoAndroid

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class Repositorio(val myItemDAO: MyItemDAO) {

    val listaMyItem: Flow<List<MyItemEntity>> = myItemDAO.loadAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(item: MyItemEntity) {
        myItemDAO.insert(item)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(item: MyItemEntity) {
        myItemDAO.delete(item)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(item: MyItemEntity) {
        myItemDAO.update(item)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun loadId(id: Int): Flow<MyItemEntity> {
        return myItemDAO.loadId(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun loadLoansOf(persona: String): Flow<MyItemEntity> {
        return myItemDAO.loadLoansOf(persona)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun loadLoansReturned(rtdFilter: Boolean = false): Flow<MyItemEntity> {
        return myItemDAO.loadLoansReturned(rtdFilter)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun loadLoansOfReturned(persona: String, rtdFilter: Boolean = false): Flow<MyItemEntity> {
        return myItemDAO.loadLoansOfReturned(persona, rtdFilter)
    }

}