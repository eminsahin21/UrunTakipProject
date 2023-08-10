package com.example.uruntakipproject

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class DepoPersoneliActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_depo_personeli)

        val textView: TextView = findViewById(R.id.txt_isim_depo)
        var depo =intent.getStringExtra("Key")
        textView.text = depo.toString()



    }
}