package com.example.uruntakipproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME="personeltakip.db"
val TABLE_NAME = "personel"
val COL_NAME ="NAME"
val COL_BIRIM ="BIRIM"
val COL_ID ="ID"
val COL_CODENO ="CODE_NO"


class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,1){

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,BIRIM TEXT, CODE_NO TEXT)")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun insertData(name:String,birimi:String,code_no:String): Boolean? {

        val p0 =this.writableDatabase
        var cv =ContentValues()
        cv.put(COL_NAME,name)
        cv.put(COL_BIRIM,birimi)
        cv.put(COL_CODENO,code_no)
        var res =p0.insert(TABLE_NAME,null,cv)

        return !res.equals(-1)



    }


    fun getAllData():Cursor{
        val p0 = this.writableDatabase
        return p0.rawQuery("SELECT * FROM $TABLE_NAME",null)
    }

    fun getData(id:String):Cursor{
        val p0 =this.writableDatabase
        return p0.rawQuery("SELECT * FROM $TABLE_NAME WHERE ID=? ", arrayOf(id),null)

    }


}
