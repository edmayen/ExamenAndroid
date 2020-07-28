package com.dev.examenandroid.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.examenandroid.R
import com.dev.examenandroid.data.DataSource
import com.dev.examenandroid.repository.ColaboradorRepositoryImpl
import com.dev.examenandroid.ui.viewmodel.ColaboradoresViewModel
import com.dev.examenandroid.ui.viewmodel.ColaboradoresViewModelFactory
import kotlinx.android.synthetic.main.fragment_colaboradores.*

class ColaboradoresFragment : Fragment() {

    private val viewModel by viewModels<ColaboradoresViewModel> { ColaboradoresViewModelFactory(ColaboradorRepositoryImpl(
        DataSource()
    ))}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_colaboradores, container, false)
    }

    private fun setupRecyclerView(){
        rvColaboradores.layoutManager = LinearLayoutManager(requireContext())
    }
}