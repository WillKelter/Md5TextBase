package com.willk.md5textbase

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import com.willk.aidl.Md5AidlInterface
import com.willk.md5textbase.databinding.ActivityMainBinding


private lateinit var binding: ActivityMainBinding
private var searchAidlInterface: Md5AidlInterface? = null

private val serviceConnection = object : ServiceConnection {
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        searchAidlInterface = Md5AidlInterface.Stub.asInterface(service)


    }

    override fun onServiceDisconnected(name: ComponentName?) {
        searchAidlInterface = null
    }
}

    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btSearch.setOnClickListener {
                var isMd5 = searchAidlInterface?.md5Check(binding.editText.text.toString())
                if(!isMd5!!){
                    Toast.makeText(applicationContext, R.string.notmd5, Toast.LENGTH_SHORT).show()}
                else {
                    if(searchAidlInterface?.md5Search(binding.editText.text.toString()) == true){
                        searchAidlInterface?.md5Add(binding.editText.text.toString())
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.ok), Toast.LENGTH_SHORT).show()
                    }

                 }
            }
        }

        override fun onStart() {
            super.onStart()
            bindService(createExplicitIntent(), serviceConnection, Context.BIND_AUTO_CREATE)
        }

        override fun onStop() {
            super.onStop()
            unbindService(serviceConnection)
        }

        private fun createExplicitIntent(): Intent {
            val intent = Intent("com.willk.md5_importexport.REMOTE_CONNECTION")
            val services = packageManager.queryIntentServices(intent, 0)
            if (services.isEmpty()) {
                Toast.makeText(applicationContext, getString(R.string.noServer), Toast.LENGTH_LONG).show()
            }
            return Intent(intent).apply {
                val resolveInfo = services[0]
                val packageName = resolveInfo.serviceInfo.packageName
                val className = resolveInfo.serviceInfo.name
                component = ComponentName(packageName, className)
            }
        }
    }
