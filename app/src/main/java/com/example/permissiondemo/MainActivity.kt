package com.example.permissiondemo

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {

    private val cameraResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
            isGranted ->
            if (isGranted){
                Toast.makeText(this,"Permission for camera ",Toast.LENGTH_SHORT).show()
            }else
            {
                Toast.makeText(this,"Permission denied for camera ",Toast.LENGTH_SHORT).show()
            }
        }

    private val cameraANDLocationResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
            permissions ->
            permissions.entries.forEach{
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted){
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(
                            this,
                            "Permission granted for location",
                            Toast.LENGTH_LONG
                        ).show()
                    }else if(permissionName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(
                            this,
                            "Permission granted for COARSE location",
                            Toast.LENGTH_LONG
                        ).show()
                    } else{
                        Toast.makeText(
                            this,
                            "Permission granted for camera",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }else{
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(
                            this,
                            "Permission denied for fine location",
                            Toast.LENGTH_LONG
                        ).show()
                    }else if(permissionName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(
                            this,
                            "Permission denied for COARSE location",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else{
                        Toast.makeText(
                            this,
                            "Permission denied for camera",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCameraPermission : Button = findViewById(R.id.btnCameraPermission)
        btnCameraPermission.setOnClickListener {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA)){
                showRationalDialog("Permission Demo requires camera acess","Camera can not be used because Camera access is denied")
            }
            else{
                cameraANDLocationResultLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                    )
            }
        }
    }

    private fun showRationalDialog(
        title: String,
        message: String
    ){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
        builder.create().show()
    }

}