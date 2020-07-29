package com.dev.examenandroid.repository

import com.dev.examenandroid.data.model.Archivo
import com.dev.examenandroid.data.model.Colaborador
import com.dev.examenandroid.data.model.ColaboradorEntity
import com.dev.examenandroid.util.Resource

interface ColaboradorRepository {
    suspend fun getColaboradorList(): Resource<List<Colaborador>>
    suspend fun getColaboradorDB(): Resource<List<ColaboradorEntity>>
    suspend fun insertColaborador(colaborador: ColaboradorEntity)
}