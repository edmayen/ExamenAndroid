package com.dev.examenandroid.ui.fragments

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.dev.examenandroid.R
import com.dev.examenandroid.data.DataSource
import com.dev.examenandroid.data.model.ColaboradorEntity
import com.dev.examenandroid.data.model.ColaboradorJson
import com.dev.examenandroid.db.ColaboradoresDatabase
import com.dev.examenandroid.repository.ColaboradorRepositoryImpl
import com.dev.examenandroid.ui.viewmodel.ColaboradoresViewModel
import com.dev.examenandroid.ui.viewmodel.ColaboradoresViewModelFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_files.*
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class FilesFragment : Fragment() {

    private val PERMISSION_REQUEST_CODE = 1234

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
        return inflater.inflate(R.layout.fragment_files, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isUnzip: Boolean = false

        btnCallService.setOnClickListener {

        }

        btnDownloadZipFile.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    // Permission Granted
                    downloadFile()
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSION_REQUEST_CODE
                    )
                }
            } else {
                // Less than M
                downloadFile()
            }
        }

        btnOpenZipFile.setOnClickListener {
            val path = context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.path+"/"
            val filename = "employees_data.json.zip"
            isUnzip = unpackZip(path, filename)
        }

        btnReadZipFile.setOnClickListener {
            if(isUnzip){
                readFile()
                showToast("File found.")
            }
            else{
                showToast("File not found.")
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
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    downloadFile()
                } else {
                    showToast("Permission Denied...")
                }
            }
        }
    }

    private fun downloadFile() {
        val url = "https://firebasestorage.googleapis.com/v0/b/example-e6943.appspot.com/o/employees_data.json.zip?alt=media&token=02daec6d-cd37-48eb-bfa5-da5862f40b97"
        try {
            val request = DownloadManager.Request(Uri.parse(url))
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setTitle("Download")
            request.setDescription("Downloading your file")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(
                requireContext(),
                Environment.DIRECTORY_DOWNLOADS,
                "/employees_data.json.zip"
            )

            val downloadManager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)

            showToast("Download complete.")

        } catch (e: Exception) {
            Log.d("Download", e.toString())
            showToast(e.toString())
        }
    }

    private fun unpackZip(path: String, zipname: String): Boolean {
        val inputMan: InputStream
        val zis: ZipInputStream
        try {
            var ze: ZipEntry? = null
            var filename: String
            val buffer = ByteArray(1024)
            var count: Int

            inputMan = FileInputStream(path + zipname)
            zis = ZipInputStream(BufferedInputStream(inputMan))

            while (zis.nextEntry.also { ze = it } != null) {
                filename = ze!!.name
                if (ze!!.isDirectory) {
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
            Log.d("UNZIP", e.toString())
            e.printStackTrace()
            return false
        }
        showToast("Unzip complete.")
        return true
    }

    private fun readFile(){
        try {
            val bufferedReader: BufferedReader = File(context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.path +"/employees_data.json").bufferedReader()
            val inputString = bufferedReader.use { it.readText()}
            parseJson(inputString)
        }catch (e: IOException){
            Log.d("READ", e.toString())
            e.printStackTrace()
        }
    }

    private fun parseJson(inputString: String){
        val gson = Gson()
        val data = gson.fromJson(inputString, ColaboradorJson::class.java)
        val employee = data.data.employees

        for (data in employee){
            viewModel.insertColaborador(ColaboradorEntity(data.id.toInt(), data.name, data.location.lat, data.location.log, data.mail))
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}