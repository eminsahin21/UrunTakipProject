package com.example.uruntakipproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.uruntakipproject.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var myDb: DatabaseHandler

    companion object {
        const val RESULT = "RESULT"
    }

    val personelNo_array: ArrayList<String> = ArrayList<String>()
    val personelBirim_array: ArrayList<String> = ArrayList<String>()
    val personelIsim_array: ArrayList<String> = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myDb = DatabaseHandler(this)


        getData()


        binding.btnTara.setOnClickListener {
            val intent = Intent(applicationContext, ScanActivity::class.java)
            startActivity(intent)
        }

        binding.adminbutton.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }

        val result = intent.getStringExtra(RESULT)

        if (result != null) {
            if (result.contains("https://") || result.contains("http://")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
                startActivity(intent)
            } else {
                binding.qrCodeText.text = result.toString()
                for (i in 0..personelNo_array.size - 1) {
                    if (personelNo_array[i] == result.toString()) {
                        println("ife girdi")
                        if (personelBirim_array[i].toInt() == 1) {
                            val intent = Intent(this, BantPersonelActivity::class.java)
                            intent.putExtra("Key", personelIsim_array[i]) //veri gönderiliyor
                            startActivity(intent)
                        } else {
                            val intent = Intent(this, DepoPersoneliActivity::class.java)
                            intent.putExtra("Key", personelIsim_array[i]) //veri gönderiliyor
                            startActivity(intent)
                        }

                    }
                }

            }

        }


    }

    private fun getData(){
        val res = myDb.getAllData()

        if(res.getCount()==0){
            showMessage("error ", "Nothing Found")
        }
        else{
            val buffer =StringBuffer()
            while (res.moveToNext()){
                buffer.append("Id: " + res.getString(0)+"\n")
                buffer.append("Name: " + res.getString(1)+"\n\n")
                buffer.append("Birimi: " + res.getString(2)+"\n\n")
                buffer.append("Code: " + res.getString(3)+"\n\n")

                personelIsim_array.add(res.getString(1).toString())
                personelBirim_array.add(res.getString(2).toString())
                personelNo_array.add(res.getString(3).toString())
            }
        }

        println(personelIsim_array)
        println(personelBirim_array)
        println(personelNo_array)
    }

    private fun showMessage(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.create()
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }


}
