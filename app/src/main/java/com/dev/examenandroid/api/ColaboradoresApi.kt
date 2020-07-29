package com.dev.examenandroid.api

import com.dev.examenandroid.data.model.ColaboradoresResponse
import com.dev.examenandroid.data.model.DataList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ColaboradoresApi {

    @GET("getFile.json?dl=0")
     fun getEmployeesData(): Call<DataList>
}