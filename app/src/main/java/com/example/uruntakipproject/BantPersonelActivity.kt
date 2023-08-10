package com.example.uruntakipproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class BantPersonelActivity : AppCompatActivity() {
    private lateinit var txtIsimBant: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_bant_personel)

            txtIsimBant = findViewById(R.id.txt_isim_bant)

            // Eğer intent ile veri alınıyorsa
            val personelIsim = intent.getStringExtra("Key")
            if (!personelIsim.isNullOrEmpty()) {
                txtIsimBant.text = personelIsim
            }
        }

        // Düğmeye tıklandığında çağrılacak fonksiyon
        fun onScanButtonClick(view: View) {
            val intent = Intent(applicationContext, ScanActivity::class.java)
            startActivity(intent)
        }
}