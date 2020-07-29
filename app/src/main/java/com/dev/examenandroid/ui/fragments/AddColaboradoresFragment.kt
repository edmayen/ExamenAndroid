package com.dev.examenandroid.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.dev.examenandroid.R
import com.dev.examenandroid.data.DataSource
import com.dev.examenandroid.data.model.Colaborador
import com.dev.examenandroid.data.model.ColaboradorEntity
import com.dev.examenandroid.db.ColaboradoresDatabase
import com.dev.examenandroid.repository.ColaboradorRepositoryImpl
import com.dev.examenandroid.ui.viewmodel.ColaboradoresViewModel
import com.dev.examenandroid.ui.viewmodel.ColaboradoresViewModelFactory
import kotlinx.android.synthetic.main.fragment_add_colaboradores.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class AddColaboradoresFragment : Fragment() {

    private val viewModel by activityViewModels<ColaboradoresViewModel> { ColaboradoresViewModelFactory(
        ColaboradorRepositoryImpl(
        DataSource(ColaboradoresDatabase.invoke(requireActivity().applicationContext))
    )
    )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_colaboradores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val latitud = Random.nextInt(1, 100).toString()
        val longitud = Random.nextInt(1, 100).toString()
        val id = Random.nextInt(1, 10000)

        btnAddNewColaborador.setOnClickListener {
            viewModel.insertColaborador(ColaboradorEntity(id ,etAddName.text.toString(), longitud, latitud, etAddEmail.text.toString()))
            Toast.makeText(requireContext(), "Guardado con exito.", Toast.LENGTH_LONG).show()
        }

    }

}