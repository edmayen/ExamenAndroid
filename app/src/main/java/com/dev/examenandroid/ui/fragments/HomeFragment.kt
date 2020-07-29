package com.dev.examenandroid.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dev.examenandroid.R
import com.dev.examenandroid.api.ColaboradoresApi
import com.dev.examenandroid.api.RetrofitInstance
import com.dev.examenandroid.data.DataSource
import com.dev.examenandroid.data.model.DataList
import com.dev.examenandroid.db.ColaboradoresDatabase
import com.dev.examenandroid.repository.ColaboradorRepositoryImpl
import com.dev.examenandroid.ui.viewmodel.ColaboradoresViewModel
import com.dev.examenandroid.ui.viewmodel.ColaboradoresViewModelFactory
import com.dev.examenandroid.util.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var authHome: FirebaseAuth

    private val viewModel by viewModels<ColaboradoresViewModel> { ColaboradoresViewModelFactory(
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService = RetrofitInstance

        btnLogout.setOnClickListener {
            // authHome.signOut()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        btnColaboradores.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_colaboradoresFragment)
        }

        btnAddColaboradores.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addColaboradoresFragment)
        }

        btnConsultaServicio.setOnClickListener {
            callService()
        }
    }

    private fun callService(){
        val userService: ColaboradoresApi = RetrofitInstance.getRestEngine().create(ColaboradoresApi::class.java)
        val result: Call<DataList> = userService.getEmployeesData()

        result.enqueue(object : Callback<DataList>{
            override fun onFailure(call: Call<DataList>, t: Throwable) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<DataList>, response: Response<DataList>) {
                Log.d("RESPUESTA SERVICIO", "RES: $response")
            }
        })
    }


}