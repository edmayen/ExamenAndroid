package com.dev.examenandroid.data

import com.dev.examenandroid.data.model.Colaborador
import com.dev.examenandroid.vo.Resource

class DataSource {
    val generateColaboradoresList = Resource.Success(listOf(
        Colaborador("Eduardo", "77.998762", "19.123465", "edu@test.com"),
        Colaborador("Adrian", "11.098756","12.098765", "ad@test.com"),
        Colaborador("Edith", "98.987698", "76.987654", "edi@test.com")
    ))
}