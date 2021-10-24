package com.example.headsuppgame

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HeadsUpDB (context: Context): SQLiteOpenHelper(context,"details.db",null,1) {
    var sqLiteDatabase: SQLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        if(db!=null){
            db.execSQL("create table celebrities (Name text, Taboo1 text, Taboo2 text, Taboo3 text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }
    fun savedat(name:String,taboo1:String,taboo2:String,taboo3:String): Long {
        val celeb= ContentValues()
        celeb.put("Name",name)
        celeb.put("Taboo1",taboo1)
        celeb.put("Taboo2",taboo2)
        celeb.put("Taboo3",taboo3)

        var status =  sqLiteDatabase.insert("celebrities",null,celeb)
        return status
    }
}