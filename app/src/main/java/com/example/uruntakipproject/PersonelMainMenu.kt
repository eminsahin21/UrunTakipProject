package com.example.uruntakipproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class PersonelMainMenu : AppCompatActivity() {

    private lateinit var personelNametext: TextView
    private lateinit var anaekranButon : ImageButton
    var personelIsim =""
    private lateinit var bantA_btn:Button
    private lateinit var bantB_btn:Button
    private lateinit var depo_btn:Button
    private lateinit var admin_btn:Button

    companion object {
        const val RESULT = "RESULT"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personel_main_menu)

        personelNametext = findViewById(R.id.personelAdiText)
        anaekranButon = findViewById(R.id.homescreenButton)

        bantA_btn = findViewById(R.id.bantaButton)
        bantB_btn = findViewById(R.id.bantBButton)
        depo_btn = findViewById(R.id.depoButton)
        admin_btn = findViewById(R.id.adminButton)

        val buttonIds = listOf(R.id.bantaButton, R.id.bantBButton, R.id.depoButton, R.id.adminButton)

        for (buttonId in buttonIds) {
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener(onButtonClickListener)
        }




        anaekranButon.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        personelIsim = intent.getStringExtra("Key").toString()




        println("İsmi: $personelIsim")

        if (!personelIsim.isNullOrEmpty()) {
            personelNametext.text = personelIsim
        }



    }


    private val onButtonClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.bantaButton -> {
                yetkiKontrol(1)
            }
            R.id.bantBButton -> {
                yetkiKontrol(2)
            }
            R.id.depoButton -> {
                yetkiKontrol(3)
            }
            R.id.adminButton -> {
                yetkiKontrol(4)
            }
        }
    }


    private fun yetkiKontrol(i: Int) {

        val personelYetkinlik =intent.getStringExtra("birim").toString()
        val elements = personelYetkinlik.split(",").filter { it.isNotBlank() }

        for (element in elements) {
            if (element == "1"){
                if (i==1){
                    val intent = Intent(this,BantPersonelActivity::class.java)
                    intent.putExtra("Pname", personelIsim) //veri gönderiliyor
                    startActivity(intent)
                }

            }
            else if (element == "2"){
                if (i==2){
                val intent = Intent(this,BantBPersonelActivity::class.java)
                intent.putExtra("Pname", personelIsim) //veri gönderiliyor
                startActivity(intent)
                }
            }
            else if (element == "3"){
                if (i==3){
                val intent = Intent(this,DepoPersoneliActivity::class.java)
                intent.putExtra("Pname", personelIsim) //veri gönderiliyor
                startActivity(intent)
                }
            }
            else if (element == "4"){
                //KONTROL ELEMANI
            }
            else if (element == "5"){
                if (i==4){

                val intent = Intent(this,AdminActivity::class.java)
                startActivity(intent)
            }
            }

        }
    }
}