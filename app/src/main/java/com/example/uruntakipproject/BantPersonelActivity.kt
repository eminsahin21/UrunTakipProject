package com.example.uruntakipproject

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.sql.Connection
import java.sql.DriverManager


class BantPersonelActivity : AppCompatActivity() {
    private lateinit var productNameInput: EditText
    private lateinit var productPriceInput: EditText
    private lateinit var addProductButton: Button
    private lateinit var txtIsimBant: TextView
    private lateinit var urunTaraButon: Button
    lateinit var myDb: UrunDatabaseHandler
    private lateinit var productsRef: DatabaseReference


    companion object {
        const val RESULT = "RESULT"
    }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_bant_personel)
            myDb = UrunDatabaseHandler(this)

            txtIsimBant = findViewById(R.id.txt_isim_bant)
            urunTaraButon = findViewById(R.id.ScanButton)
            productNameInput = findViewById(R.id.productName)
            productPriceInput = findViewById(R.id.productPrice)
            addProductButton = findViewById(R.id.addProductButton)

            productsRef = FirebaseDatabase.getInstance().reference.child("products")

            // Eğer intent ile veri alınıyorsa
            val personelIsim = intent.getStringExtra("Pname")
            if (!personelIsim.isNullOrEmpty()) {
                txtIsimBant.text = personelIsim
            }


           urunTaraButon.setOnClickListener {
               val intent = Intent(applicationContext, UrunScanActivity::class.java)
               intent.putExtra("where","A" ) //veri gönderiliyor
               startActivity(intent)
           }

            val result = intent.getStringExtra(RESULT)
            println("sonuc bu ---------"+ result)

            if (result != null){
                txtIsimBant.text = result.toString()
//                AddDataToDB(result)
            }


            addProductButton.setOnClickListener {
                val productName = productNameInput.text.toString()
                val productPrice = productPriceInput.text.toString().toDoubleOrNull()

                if (!productName.isNullOrEmpty() && productPrice != null) {
                    val newProduct = mapOf(
                        "name" to productName,
                        "price" to productPrice
                    )

//                    val barcode = "7890123456789" // Örnek barkod
                    val productRef = productsRef.child(result.toString())

                    productRef.setValue(newProduct)
                        .addOnSuccessListener {
                            println("Ürün başarıyla eklendi.")
                        }
                        .addOnFailureListener { error ->
                            println("Ürün eklenirken hata oluştu: $error")
                        }

                    // Girdi alanlarını temizle
                    productNameInput.text.clear()
                    productPriceInput.text.clear()
                } else {
                    println("Geçersiz veri girişi.")
                }
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