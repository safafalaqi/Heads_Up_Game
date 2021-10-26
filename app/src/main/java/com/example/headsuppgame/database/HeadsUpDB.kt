package com.example.headsuppgame.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.headsuppgame.model.Celebrities
import com.example.headsuppgame.model.CelebritiesItem
import java.lang.Exception

class HeadsUpDB (val context: Context): SQLiteOpenHelper(context,"celebrities.db",null,1) {
    var sqLiteDatabase: SQLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        if(db!=null){
            db.execSQL("CREATE TABLE celebrities (_id integer PRIMARY KEY autoincrement,Name text, Taboo1 text, Taboo2 text, Taboo3 text)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS celebrities")
        onCreate(sqLiteDatabase)
    }
    fun saveData(celebritiesItem: CelebritiesItem): Long {
        val celeb = ContentValues()
        celeb.put("Name", celebritiesItem.name)
        celeb.put("Taboo1", celebritiesItem.taboo1)
        celeb.put("Taboo2", celebritiesItem.taboo2)
        celeb.put("Taboo3", celebritiesItem.taboo3)

        return sqLiteDatabase.insert("celebrities", null, celeb)
    }

    fun readData(): Celebrities {
        val cList= Celebrities()
        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM celebrities",null)

        if(cursor.count<1)
        {
           return cList
        }else
        {
            while(cursor.moveToNext())
            {
                val pk = cursor.getInt(0)
                val name = cursor.getString(1)
                val taboo1= cursor.getString(2)
                val taboo2= cursor.getString(3)
                val taboo3= cursor.getString(4)
                cList.add(CelebritiesItem(name,taboo1,taboo2,taboo3,pk))
            }
        }
        return cList
    }

    fun updateData(pk: Int, name: String,taboo1:String,taboo2:String,taboo3:String){

        try {
            val cv=ContentValues()
            cv.put("Name",name)
            cv.put("Taboo1",taboo1)
            cv.put("Taboo2",taboo2)
            cv.put("Taboo3",taboo3)
            var status= sqLiteDatabase.update("celebrities",cv,"_id = $pk",null)
            Toast.makeText(context, "Update success! $status", Toast.LENGTH_SHORT)
                .show()

        }catch (e: Exception){
            Toast.makeText(context,"Can not Update! ",  Toast.LENGTH_SHORT  ).show()

        }
    }

    fun deleteData(celebrity: CelebritiesItem) {
        try {
            sqLiteDatabase.delete("celebrities","_id = ${celebrity.pk}",null)
            Toast.makeText(context,"Delete success! ",  Toast.LENGTH_SHORT  ).show()
        }catch (e: Exception){
            Toast.makeText(context,"Can not delete! ",  Toast.LENGTH_SHORT  ).show()
        }
    }
}