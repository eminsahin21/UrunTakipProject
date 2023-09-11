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
import com.google.firebase.database.*
import java.sql.Connection
import java.sql.DriverManager


class BantBPersonelActivity : AppCompatActivity() {
    private lateinit var productNameInput: EditText
    private lateinit var productPriceInput: EditText
    private lateinit var productCategoryInput: EditText
    private lateinit var productDetailsInput: EditText
    private lateinit var addProductButton: Button
    private lateinit var txtIsimBant: TextView
    private lateinit var urunTaraButon: Button
    lateinit var myDb: UrunDatabaseHandler
    lateinit var result: String
    private lateinit var productsRef: DatabaseReference


    companion object {
        const val RESULT = "RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bant_bpersonel)
        myDb = UrunDatabaseHandler(this)

        txtIsimBant = findViewById(R.id.txt_isim_bant)
        urunTaraButon = findViewById(R.id.ScanButton)
        productNameInput = findViewById(R.id.productName)
        productPriceInput = findViewById(R.id.productPrice)
        addProductButton = findViewById(R.id.addProductButton)
        productCategoryInput = findViewById(R.id.productCategory)
        productDetailsInput = findViewById(R.id.productDetails)



        productsRef = FirebaseDatabase.getInstance().reference.child("products")

        // Eğer intent ile veri alınıyorsa
        val personelIsim = intent.getStringExtra("Pname")
        if (!personelIsim.isNullOrEmpty()) {
            txtIsimBant.text = personelIsim
        }


        urunTaraButon.setOnClickListener {
            val intent = Intent(applicationContext, UrunScanActivity::class.java)
            intent.putExtra("where","B" ) //veri gönderiliyor
            startActivity(intent)
        }

        result = intent.getStringExtra(RESULT).toString()

        if (result != null){
            txtIsimBant.text = result.toString()
//            AddDataToDB(result)
            println("result------------"+ result)
            val childKey = result.toString().trim()
            productsRef.child(childKey).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // "name" ve "price" değerlerini okuyun
                        val productName = dataSnapshot.child("name").getValue(String::class.java)
                        val productPrice = dataSnapshot.child("price").getValue(String::class.java)

                        if (productName != null && productPrice != null) {
                            // Verileri yazdırın veya başka bir işlem yapın
                            println("Ürün Adı: $productName")
                            println("Ürün Fiyatı: $productPrice")
                            productNameInput.setText(productName.toString())
                            productPriceInput.setText(productPrice.toString())
                        } else {
                            println("Veriler eksik veya hatalı")
                        }
                    } else {
                        println("Belirtilen anahtarla hiçbir veri bulunamadı")
                    }
                }




                override fun onCancelled(databaseError: DatabaseError) {
                    // Hata durumu
                }
            })


        }
        addProductButton.setOnClickListener {
            // Güncellenecek ürünün anahtarını alın
            if (result != null) {
                val childKey = result.trim()

                // Kullanıcıdan alınan kategori ve detayları alın
                val productCategory = productCategoryInput.text.toString()
                val productDetails = productDetailsInput.text.toString()

                // Kategori ve detayların boş olup olmadığını kontrol edin
                if (productCategory.isNotEmpty() && productDetails.isNotEmpty()) {
                    // Güncelleme verilerini oluşturun
                    val updates = hashMapOf<String, Any>(
                        "kategori" to productCategory,
                        "details" to productDetails
                    )

                    // Veriyi Firebase'de güncelle
                    productsRef.child(childKey).updateChildren(updates)
                        .addOnSuccessListener {
                            // Güncelleme başarılı
                            Toast.makeText(applicationContext, "Veri Güncellendi", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            // Güncelleme başarısız
                            Toast.makeText(applicationContext, "Veri Güncelleme Hatası", Toast.LENGTH_SHORT).show()
                        }

                        productNameInput.text.clear()
                        productPriceInput.text.clear()
                        productCategoryInput.text.clear()
                        productDetailsInput.text.clear()

                } else {
                    // Kategori veya detaylar boş ise kullanıcıya bir hata mesajı gösterin
                    Toast.makeText(applicationContext, "Kategori ve Detayları Doldurun", Toast.LENGTH_SHORT).show()
                }
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