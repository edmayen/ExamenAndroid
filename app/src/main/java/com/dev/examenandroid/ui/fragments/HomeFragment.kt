package com.dev.examenandroid.ui.fragments

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.jar.Manifest
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class HomeFragment : Fragment() {

    private val STORAGE_PERMISSION_CODE: Int = 1000
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
            //callService()
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(ContextCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    requestPermissions(arrayOf(WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                } else {
                    downloadZip()
                }
            } else{
                downloadZip()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            STORAGE_PERMISSION_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    downloadZip()
                } else {
                    Toast.makeText(requireContext(), "Permiso denegado", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


     fun downloadZip(){
         val url = "https://firebasestorage.googleapis.com/v0/b/example-e6943.appspot.com/o/employees_data.json.zip?alt=media&token=02daec6d-cd37-48eb-bfa5-da5862f40b97"
         val request = DownloadManager.Request(
             Uri.parse(url))
             .setTitle("Descargando...")
             .setDescription("Descargando Archivo...")
             .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
             .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
             .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS,"/employees_data.json.zip")

        var manager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)

        val path = context?.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!.path+"/"
        val filename = "employees_data.json.zip"
        if(unpackZip(path, filename)){
            readFile()
        }
    }

    private fun unpackZip(path: String, zipname: String): Boolean {
        val inputMan: InputStream
        val zis: ZipInputStream
        try {
            var ze: ZipEntry
            var filename: String
            val buffer = ByteArray(2048)
            var count: Int

            inputMan = FileInputStream(path + zipname)
            zis = ZipInputStream(BufferedInputStream(inputMan))

            while (zis.nextEntry.also { ze = it } != null) {
                filename = ze.name
                if (ze.isDirectory) {
                    val fmd = File(path + filename)
                    fmd.mkdirs()
                    continue
                }
                val fout = FileOutputStream(path + filename)
                while (zis.read(buffer).also { count = it } != -1) {
                    fout.write(buffer, 0, count)
                }
                fout.close()
                zis.closeEntry()
            }
            zis.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun readFile(){
        try {
            val bufferedReader: BufferedReader = File(context?.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!.path +"/employees_data.json").bufferedReader()
            val inputString = bufferedReader.use { it.readText()}
            parseJson(inputString)
        }catch (e:IOException){
            e.printStackTrace()
        }
    }

    private fun parseJson(inputString: String){
        Log.d("JSON", inputString)
    }

    private fun callService(){
        val userService: ColaboradoresApi = RetrofitInstance.getRestEngine().create(ColaboradoresApi::class.java)
        val result: Call<DataList> = userService.getEmployeesData()

        result.enqueue(object : Callback<DataList>{
            override fun onFailure(call: Call<DataList>, t: Throwable) {
                Toast.makeText(requireContext(), "Error $t", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<DataList>, response: Response<DataList>) {
                Log.d("RESPUESTA SERVICIO", Gson().toJson(response.body()))
            }
        })
    }


}