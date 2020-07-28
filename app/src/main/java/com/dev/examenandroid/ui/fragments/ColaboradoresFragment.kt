package com.dev.examenandroid.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.examenandroid.R
import com.dev.examenandroid.adapters.ColaboradorAdapter
import com.dev.examenandroid.data.DataSource
import com.dev.examenandroid.data.model.Colaborador
import com.dev.examenandroid.repository.ColaboradorRepositoryImpl
import com.dev.examenandroid.ui.viewmodel.ColaboradoresViewModel
import com.dev.examenandroid.ui.viewmodel.ColaboradoresViewModelFactory
import com.dev.examenandroid.vo.Resource
import kotlinx.android.synthetic.main.fragment_colaboradores.*

class ColaboradoresFragment : Fragment(), ColaboradorAdapter.OnColaboradorCLickListener {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.fetchColaboradoresList.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    rvColaboradores.adapter = ColaboradorAdapter(requireContext(), result.data, this)
                }
                is Resource.Failure -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Error al traer los datos ${result.exception}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun hideProgressBar(){
        progressBarColaborador.visibility = View.GONE
    }

    private fun showProgressBar(){
        progressBarColaborador.visibility = View.VISIBLE
    }

    private fun setupRecyclerView(){
        rvColaboradores.layoutManager = LinearLayoutManager(requireContext())
        rvColaboradores.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    override fun onColaboradorClick(colaborador: Colaborador) {
        val bundle = Bundle()
        bundle.putParcelable("colaborador", colaborador)
        TODO("Go to maps fragment")
        // pasar informacion a otro fragment
        // findNavController().navigate(R.id.MAPS, bundle)
    }
}