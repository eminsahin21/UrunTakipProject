package com.example.uruntakipproject

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.sql.Connection
import java.sql.DriverManager


class BantPersonelActivity : AppCompatActivity() {
    private lateinit var txtIsimBant: TextView
    private lateinit var urunTaraButon: Button
    lateinit var myDb: UrunDatabaseHandler
    lateinit var strictmode : StrictMode

    companion object {
        const val RESULT = "RESULT"
    }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_bant_personel)
            myDb = UrunDatabaseHandler(this)

            txtIsimBant = findViewById(R.id.txt_isim_bant)
            urunTaraButon = findViewById(R.id.ScanButton)

            // Eğer intent ile veri alınıyorsa
            val personelIsim = intent.getStringExtra("Key")
            if (!personelIsim.isNullOrEmpty()) {
                txtIsimBant.text = personelIsim
            }


           urunTaraButon.setOnClickListener {
               val intent = Intent(applicationContext, UrunScanActivity::class.java)
               startActivity(intent)
           }

            val result = intent.getStringExtra(RESULT)

            if (result != null){
                txtIsimBant.text = result.toString()
                AddDataToDB(result)
            }


        }

        private fun AddDataToDB(result: String) {
            val ilkbarkod = result

            val isInserted = myDb.insertData(ilkbarkod)
            if (isInserted==true){
                Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Data eklenemedi", Toast.LENGTH_SHORT).show()
            }




        }

}