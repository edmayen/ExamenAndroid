package com.dev.examenandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.examenandroid.repository.ColaboradorRepository

class ColaboradoresViewModelFactory(private val repo: ColaboradorRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ColaboradorRepository::class.java).newInstance(repo)
    }
}