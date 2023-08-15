package com.example.uruntakipproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME_="uruntakip.db"
val TABLE_NAME_ = "urunler"
val COL_ID_ ="ID"
val COL_ULKEKOD ="ULKE_KOD"
val COL_ISLETMEKOD ="ISLETME_KOD"
val COL_URUNKOD_ ="URUN_KOD"
val COL_KONTROLKOD_ ="KONTROL_KOD"
val COL_BARKOD_ ="BARKOD_NO"

class UrunDatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME_,null,1){

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME_(ID INTEGER PRIMARY KEY AUTOINCREMENT,ULKE_KOD TEXT,ISLETME_KOD TEXT, URUN_KOD TEXT,KONTROL_KOD TEXT,BARKOD_NO TEXT)")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun insertData(barkodno:String): Boolean? {
        val p0 =this.writableDatabase
        var cv =ContentValues()

        val ulke_kod = barkodno.substring(0, 3)
        val isletme_kod = barkodno.substring(3, 7)
        val urun_kod = barkodno.substring(7, 12)
        val kontrolhanesi = barkodno.substring(12)

        println("İlk 3 hane: $ulke_kod")
        println("Sonraki 4 hane: $isletme_kod")
        println("Sonraki 5 hane: $urun_kod")
        println("Son 1 hane: $kontrolhanesi")


        cv.put(COL_ULKEKOD,ulke_kod)
        cv.put(COL_ISLETMEKOD,isletme_kod)
        cv.put(COL_URUNKOD_,urun_kod)
        cv.put(COL_KONTROLKOD_,kontrolhanesi)
        cv.put(COL_BARKOD_,barkodno)

        var res =p0.insert(TABLE_NAME_,null,cv)

        return !res.equals(-1)

    }


    fun getAllData():Cursor{
        val p0 = this.writableDatabase
        return p0.rawQuery("SELECT * FROM $TABLE_NAME_",null)
    }

    fun getData(id:String):Cursor{
        val p0 =this.writableDatabase
        return p0.rawQuery("SELECT * FROM $TABLE_NAME_ WHERE ID=? ", arrayOf(id),null)

    }


}