package com.dev.examenandroid.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.dev.examenandroid.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : Fragment() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        auth.signOut()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRegistrar.setOnClickListener {
            registerUser()
        }

        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }

    private fun registerUser(){
        val emailReg = etNewUser.text.toString()
        val passwordReg = etNewPassword.text.toString()
        var isRegistered = false
        if(emailReg.isNotEmpty() && passwordReg.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    auth.createUserWithEmailAndPassword(emailReg, passwordReg)
                    withContext(Dispatchers.Main){
                        isRegistered = checkLoggedInState()
                        if (isRegistered){
                            Toast.makeText(requireContext(), "Usuario registrado, ahora puede iniciar sesion.", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Usuario o contraseña invalidos.", Toast.LENGTH_LONG).show()
        }
    }

    private fun loginUser(){
        val emailLogin = etUser.text.toString()
        val passwordLogin = etPassword.text.toString()
        var isLogged = false
        if(emailLogin.isNotEmpty() && passwordLogin.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    auth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                    withContext(Dispatchers.Main){
                        isLogged = checkLoggedInState()
                        if (isLogged){
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    }
                } catch (e: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else{
            Toast.makeText(requireContext(), "Usuario o contrasea incorrectos.", Toast.LENGTH_LONG).show()
        }

    }

    private fun checkLoggedInState(): Boolean{
        if(auth.currentUser != null){
            return true
        } else{
            Toast.makeText(requireContext(), "You are not logged in.", Toast.LENGTH_LONG).show()
        }
        return false
    }

}