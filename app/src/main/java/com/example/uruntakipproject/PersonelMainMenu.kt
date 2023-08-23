package com.example.uruntakipproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PersonelMainMenu : AppCompatActivity() {

    private lateinit var personelNametext: TextView
    companion object {
        const val RESULT = "RESULT"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personel_main_menu)

        personelNametext = findViewById(R.id.personelAdiText)

        val personelIsim = intent.getStringExtra("Key")
        val personelYetkinlik =intent.getStringExtra("birim")
        if (!personelIsim.isNullOrEmpty()) {
            personelNametext.text = personelIsim
        }



    }
}