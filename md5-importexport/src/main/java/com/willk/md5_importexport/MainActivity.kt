package com.willk.md5_importexport

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.willk.md5_importexport.databinding.ActivityMainBinding
import com.willk.md5_importexport.domain.LocalRepository
import com.willk.md5_importexport.domain.Md5Entity
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter


private lateinit var binding:ActivityMainBinding
private var isBaseLoaded = false
private const val BASE_KEY = "BASE_LOADED"
private var md5String = Md5Entity()
private const val READ_CODE = 1
private const val WRITE_CODE = 2
private lateinit var list: List<Md5Entity>

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = LocalRepository(this).getMd5()
        if (list.isNotEmpty()) {
            binding.textView.text = getString(R.string.base_loaded)
        } else {
            binding.textView.text = getString(R.string.base_not_loaded)
        }

        binding.btImport.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "text/plain"
            startActivityForResult(intent, READ_CODE)

        }

        binding.btExport.setOnClickListener {

            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
            intent.type = "text/plain"
            startActivityForResult(intent, WRITE_CODE)



            }
        }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == WRITE_CODE) {
            if (data != null) {
                data.data?.let { writeFile(it) }
            }
        }

        if(requestCode == READ_CODE) {
            if (data != null) {
                data.data?.let { readFile(it) }
            }
        }
    }



    private fun readFile(path: Uri){
        LocalRepository(this).deleteMd5()
        val inputStreamReader = InputStreamReader(contentResolver.openInputStream(path))
        val bufferedReader = BufferedReader(inputStreamReader)
        bufferedReader.forEachLine {
            md5String.md5 = it
            LocalRepository(this).insertMd5(md5String)  }
        bufferedReader.close()
        binding.textView.text = getString(R.string.base_loaded)

    }

    private fun writeFile(path: Uri,){
        list = LocalRepository(this).getMd5()
        val outputStreamWriter = OutputStreamWriter(contentResolver.openOutputStream(path))
        val bufferedWriter = BufferedWriter(outputStreamWriter)
        bufferedWriter.use {out -> list.forEach{
            out.write(it.md5)
            out.newLine()
        } }
        bufferedWriter.close()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(BASE_KEY, isBaseLoaded)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isBaseLoaded = savedInstanceState.getBoolean(BASE_KEY)
    }
}