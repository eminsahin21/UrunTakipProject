package com.example.uruntakipproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class AdminActivity : AppCompatActivity() {

    private lateinit var nameText: EditText
    private lateinit var birimText: EditText
    private lateinit var codeText: EditText
    private lateinit var buttonEkle: Button
    private lateinit var eklenenKayitText: TextView
    lateinit var myDb:DatabaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        myDb = DatabaseHandler(this)


        nameText = findViewById(R.id.nameText)
        birimText = findViewById(R.id.birimText)
        codeText = findViewById(R.id.codeText)
        buttonEkle = findViewById(R.id.button_ekle)
        eklenenKayitText = findViewById(R.id.eklenenKayitText)


        AddData()

    }
    private fun AddData() {

            buttonEkle.setOnClickListener(View.OnClickListener setOnClickListener@{
                val name = nameText.text.toString().trim()
                val birim = birimText.text.toString().trim()
                val codeNo = codeText.text.toString().trim()

                if (TextUtils.isEmpty(name)){
                    nameText.error="Enter Name"
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(birim)){
                    codeText.error="Enter birim no"
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(codeNo)){
                    birimText.error="Enter codeNo"
                    return@setOnClickListener
                }
                val isInserted = myDb.insertData(name,birim,codeNo)
                if (isInserted==true){
                    Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Data eklenemedi", Toast.LENGTH_SHORT).show()
                }

            })
    }


}