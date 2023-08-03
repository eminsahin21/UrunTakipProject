package com.example.uruntakipproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASE_NAME="MyDB"
val TABLE_NAME = "deneme"
val COL_NAME ="personelName"
val COL_BIRIM ="birimi"
val COL_ID ="id"
val COL_CODENO ="code_no"

class DatabaseHandler(context:Context): SQLiteOpenHelper(context, DATABASE_NAME,null,1){
    override fun onCreate(p0: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

}