package com.dev.examenandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dev.examenandroid.data.model.ColaboradorEntity
import com.dev.examenandroid.repository.ColaboradorRepository
import com.dev.examenandroid.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ColaboradoresViewModel(private val repo: ColaboradorRepository): ViewModel() {

    val fetchColaboradoresList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getColaboradorList())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun insertColaborador(colaborador: ColaboradorEntity){
        viewModelScope.launch {
            repo.insertColaborador(colaborador)
        }
    }

    fun getColaboradorDB() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getColaboradorDB())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}