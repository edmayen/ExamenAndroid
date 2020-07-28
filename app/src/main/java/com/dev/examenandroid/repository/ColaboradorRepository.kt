package com.dev.examenandroid.repository

import com.dev.examenandroid.data.model.Colaborador
import com.dev.examenandroid.vo.Resource

interface ColaboradorRepository {
    fun getColaboradorList(): Resource<List<Colaborador>>
}