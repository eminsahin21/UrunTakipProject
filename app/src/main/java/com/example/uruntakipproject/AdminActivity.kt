package com.example.uruntakipproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AdminActivity : AppCompatActivity() {

    private lateinit var nameText: EditText
    private lateinit var birimText: EditText
    private lateinit var codeText: EditText
    private lateinit var buttonEkle: Button
    private lateinit var eklenenKayitText: TextView

    private lateinit var bantA_cbox : CheckBox
    private lateinit var bantB_cbox : CheckBox
    private lateinit var depo_cbox : CheckBox
    private lateinit var kontrol_cbox : CheckBox
    private lateinit var admin_cbox : CheckBox

    lateinit var myDb:DatabaseHandler
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        myDb = DatabaseHandler(this)


        nameText = findViewById(R.id.nameText)
        codeText = findViewById(R.id.codeText)
        buttonEkle = findViewById(R.id.button_ekle)
        eklenenKayitText = findViewById(R.id.eklenenKayitText)


        bantA_cbox = findViewById(R.id.checkBox1)
        bantB_cbox = findViewById(R.id.checkBox2)
        depo_cbox = findViewById(R.id.checkBox3)
        kontrol_cbox = findViewById(R.id.checkBox4)
        admin_cbox = findViewById(R.id.checkBox5)


        dbRef = FirebaseDatabase.getInstance().getReference("Personel");
        AddData()





    }


    private fun AddData() {

            buttonEkle.setOnClickListener(View.OnClickListener setOnClickListener@{

                val name = nameText.text.toString().trim()
                val codeNo = codeText.text.toString().trim()

                if (TextUtils.isEmpty(name)){
                    nameText.error="Enter Name"
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(codeNo)){
                    codeText.error="Enter codeNo"
                    return@setOnClickListener
                }


                var birim = ""

                if (bantA_cbox.isChecked){
                    birim += "1,"
                }
                if (bantB_cbox.isChecked){
                    birim += "2,"
                }
                if (depo_cbox.isChecked){
                    birim += "3,"
                }
                if (kontrol_cbox.isChecked){
                    birim += "4,"
                }
                if (admin_cbox.isChecked){
                    birim += "5,"
                }


                println("Birimi:{$birim}")

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
                    Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Data eklenemedi", Toast.LENGTH_SHORT).show()
                }

            })
    }


}