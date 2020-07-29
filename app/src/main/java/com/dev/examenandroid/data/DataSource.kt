package com.dev.examenandroid.data

import com.dev.examenandroid.api.RetrofitInstance
import com.dev.examenandroid.data.model.Archivo
import com.dev.examenandroid.data.model.Colaborador
import com.dev.examenandroid.data.model.ColaboradorEntity
import com.dev.examenandroid.db.ColaboradoresDatabase
import com.dev.examenandroid.util.Resource

class DataSource(private val appDatabase: ColaboradoresDatabase) {

    suspend fun insertColaboradorRoom(colaborador: ColaboradorEntity){
        appDatabase.getColaboradorDao().insertColaborador(colaborador)
    }

    suspend fun getColaboradorDB(): Resource<List<ColaboradorEntity>> {
        return Resource.Success(appDatabase.getColaboradorDao().getAllColaboradores())

    }

    val generateColaboradoresList = Resource.Success(listOf(
        Colaborador("Eduardo", "77.998762", "19.123465", "edu@test.com"),
        Colaborador("Adrian", "11.098756","12.098765", "ad@test.com"),
        Colaborador("Edith", "98.987698", "76.987654", "edi@test.com")
    ))
}