package com.example.uruntakipproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AdminActivity : AppCompatActivity() {

    private lateinit var nameText: EditText
    private lateinit var birimText: EditText
    private lateinit var codeText: EditText
    private lateinit var buttonEkle: Button
    private lateinit var eklenenKayitText: TextView
    lateinit var myDb:DatabaseHandler
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        myDb = DatabaseHandler(this)


        nameText = findViewById(R.id.nameText)
        birimText = findViewById(R.id.birimText)
        codeText = findViewById(R.id.codeText)
        buttonEkle = findViewById(R.id.button_ekle)
        eklenenKayitText = findViewById(R.id.eklenenKayitText)


        dbRef = FirebaseDatabase.getInstance().getReference("Personel");

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
                    birimText.error="Enter birim no"
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(codeNo)){
                    codeText.error="Enter codeNo"
                    return@setOnClickListener
                }
                val perId =dbRef.push().key!!
                val personel = PersonelModel(perId,name,birim,codeNo)

                dbRef.child(name).setValue(personel)
                    .addOnCompleteListener{
                        Toast.makeText(this,"Data Eklendi Firebase",Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{err->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                        
                    }


                val isInserted = myDb.insertData(name,birim,codeNo)
                if (isInserted==true){
                    nameText.text.clear()
                    codeText.text.clear()
                    birimText.text.clear()
                    Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Data eklenemedi", Toast.LENGTH_SHORT).show()
                }

            })
    }


}