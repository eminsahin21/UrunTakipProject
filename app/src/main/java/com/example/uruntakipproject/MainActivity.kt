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
    lateinit var myDb:DatabaseHandler

    companion object {
        const val RESULT = "RESULT"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myDb = DatabaseHandler(this)


        AddData()
//        getData()

        val res = myDb.getAllData()

        if(res.getCount()==0){
            showMessage("error ", "Nothing Found")
//            return@OnClickListener
        }
        else{
            val buffer =StringBuffer()
            while (res.moveToNext()){
                buffer.append("Id: " + res.getString(0)+"\n")
                buffer.append("Name: " + res.getString(1)+"\n\n")
                buffer.append("Birimi: " + res.getString(2)+"\n\n")
                buffer.append("Code: " + res.getString(3)+"\n\n")
            }
//                showMessage("Data",buffer.toString())
//            println("mehmet emşin")
        }



            binding.btnTara.setOnClickListener {
                val intent = Intent(applicationContext, ScanActivity::class.java)
                startActivity(intent)
            }

            val result = intent.getStringExtra(RESULT)

            if (result != null) {
                if (result.contains("https://") || result.contains("http://")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
                    startActivity(intent)
                } else {
                    binding.qrCodeText.text = result.toString()

                }

            }


        }

    private fun getData() {
        binding.getData.setOnClickListener(View.OnClickListener {


                
                
        })
    }

    private fun showMessage(title: String, message: String) {
        val builder =AlertDialog.Builder(this)
        builder.create()
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }

    private fun AddData() {
        binding.btninsert.setOnClickListener(View.OnClickListener setOnClickListener@{
            val name = binding.nameText.text.toString().trim()
            val birim = binding.birimText.text.toString().trim()
            val codeNo = binding.codeText.text.toString().trim()

            if (TextUtils.isEmpty(name)){
                binding.nameText.error="Enter Name"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(birim)){
                binding.nameText.error="Enter birim no"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(codeNo)){
                binding.nameText.error="Enter codeNo"
                return@setOnClickListener
            }
            val isInserted =myDb.insertData(name,birim,codeNo)
            if (isInserted==true){
                Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Data eklenemedi", Toast.LENGTH_SHORT).show()
            }

        })


    }
}
