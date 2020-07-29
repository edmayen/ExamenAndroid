package com.dev.examenandroid.repository

import com.dev.examenandroid.data.DataSource
import com.dev.examenandroid.data.model.Archivo
import com.dev.examenandroid.data.model.Colaborador
import com.dev.examenandroid.data.model.ColaboradorEntity
import com.dev.examenandroid.util.Resource

class ColaboradorRepositoryImpl(private val dataSource: DataSource): ColaboradorRepository {

    override suspend fun getColaboradorList(): Resource<List<Colaborador>> {
        return dataSource.generateColaboradoresList
    }

    override suspend fun getColaboradorDB(): Resource<List<ColaboradorEntity>> {
        return dataSource.getColaboradorDB()
    }

    override suspend fun insertColaborador(colaborador: ColaboradorEntity) {
        dataSource.insertColaboradorRoom(colaborador)
    }
}