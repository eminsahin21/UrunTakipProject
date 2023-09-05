package com.example.uruntakipproject

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    var scannerView: ZXingScannerView? = null
    lateinit var myDb: DatabaseHandler


    val personelNo_array: ArrayList<String> = ArrayList<String>()
    val personelBirim_array: ArrayList<String> = ArrayList<String>()
    val personelIsim_array: ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
        myDb = DatabaseHandler(this)

        setPermission()
        getData()
    }

    private fun getDataFirebase(result: String) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.reference.child("Personel")

        val personelList: MutableList<PersonelModel> = mutableListOf()

        // Verileri okuma işlemi
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (personelSnapshot in dataSnapshot.children) {
                    val personel = personelSnapshot.getValue(PersonelModel::class.java)
                    personel?.let {
                        personelList.add(it)
                    }
                }

                // Verileri diziye attıktan sonra yapmak istediğiniz işlemleri burada gerçekleştirebilirsiniz
                for (personel in personelList) {
                    println("Ad: ${personel.perName}")

                    for (personel in personelList) {
                    if (personel.perCode== result) {

                        val intent = Intent(applicationContext, PersonelMainMenu::class.java)
                        intent.putExtra("Key", personel.perName) //veri gönderiliyor
                        intent.putExtra("birim", personel.perBirim) //veri gönderiliyor
                        startActivity(intent)

                    }
                }




                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Veri okuma hatası: $error")
            }
        })
    }

    override fun handleResult(p0: Result?) {
        var x = 0
        var result = p0.toString()
        getDataFirebase(result)

//        if (result != null) {
//
//            val intent = Intent(applicationContext, PersonelMainMenu::class.java)
//
//            startActivity(intent)

//            if (result.contains("https://") || result.contains("http://")) {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
//                startActivity(intent)
//            } else {
//                for (i in 0..personelNo_array.size - 1) {
//                    if (personelNo_array[i] == result) {
//                        println("ife girdi")
//                        if (personelBirim_array[i].toInt() == 1) {
//                            val intent = Intent(this, BantPersonelActivity::class.java)
//                            intent.putExtra("Key", personelIsim_array[i]) //veri gönderiliyor
//                            x = 1
//                            startActivity(intent)
//                        } else if (personelBirim_array[i].toInt() == 2) {
//                            val intent = Intent(this, DepoPersoneliActivity::class.java)
//                            intent.putExtra("Key", personelIsim_array[i]) //veri gönderiliyor
//                            x = 1
//                            startActivity(intent)
//                        }else if(personelBirim_array[i].toInt() == 1940){
//                            val intent = Intent(this, AdminActivity::class.java)
//                            x = 1
//                            startActivity(intent)}
//
//                    }
//                }
//                if (x == 0) {
//                    finish()
//                    Toast.makeText(this,"Personel Bulunamadı!",Toast.LENGTH_SHORT).show()
//                }
//
//            }

//        }

    }

    override fun onResume() {
        super.onResume()
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()

    }

    override fun onStop() {
        super.onStop()

        scannerView?.stopCamera()
        onBackPressed()

    }


    private fun setPermission() {

        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 1)

    }

    private fun getData() {
        val res = myDb.getAllData()

        if (res.getCount() == 0) {
            showMessage("error ", "Nothing Found")
        } else {
            val buffer = StringBuffer()
            while (res.moveToNext()) {
                buffer.append("Id: " + res.getString(0) + "\n")
                buffer.append("Name: " + res.getString(1) + "\n\n")
                buffer.append("Birimi: " + res.getString(2) + "\n\n")
                buffer.append("Code: " + res.getString(3) + "\n\n")

                personelIsim_array.add(res.getString(1).toString())
                personelBirim_array.add(res.getString(2).toString())
                personelNo_array.add(res.getString(3).toString())
            }
        }

        println(personelIsim_array)
        println(personelBirim_array)
        println(personelNo_array)
    }

    private fun showMessage(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.create()
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        applicationContext,
                        "Kameraya izin gerekiyor!",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }


}