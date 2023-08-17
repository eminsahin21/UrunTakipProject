package com.example.uruntakipproject

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.uruntakipproject.MainActivity.Companion.RESULT
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class UrunScanActivity : AppCompatActivity(),ZXingScannerView.ResultHandler{

    var scannerView_ : ZXingScannerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView_ = ZXingScannerView(this)
        setContentView(scannerView_)

        setPermission()

    }


    override fun handleResult(p0: Result?) {
        val intent = Intent(applicationContext,BantPersonelActivity::class.java)
        intent.putExtra(RESULT,p0.toString())
        startActivity(intent)
    }




    override fun onResume() {
        super.onResume()
        scannerView_?.setResultHandler(this)
        scannerView_?.startCamera()

    }

    override fun onStop() {
        super.onStop()

        scannerView_?.stopCamera()
        onBackPressed()

    }


    private fun setPermission(){

        val permission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),1)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1 -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(applicationContext,"Kameraya izin gerekiyor!",Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}