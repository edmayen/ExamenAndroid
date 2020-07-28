package com.dev.examenandroid.repository

import com.dev.examenandroid.data.DataSource
import com.dev.examenandroid.data.model.Colaborador
import com.dev.examenandroid.vo.Resource

class ColaboradorRepositoryImpl(private val dataSource: DataSource): ColaboradorRepository {

    override fun getColaboradorList(): Resource<List<Colaborador>> {
        return dataSource.getColaboradoresList()
    }
}