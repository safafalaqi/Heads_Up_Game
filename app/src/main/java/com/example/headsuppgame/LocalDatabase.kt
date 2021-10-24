package com.example.headsuppgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LocalDatabase : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etTaboo1: EditText
    lateinit var etTaboo2: EditText
    lateinit var etTaboo3: EditText
    lateinit var btAdd: Button

    var name= ""
    var tab1= ""
    var tab2= ""
    var tab3= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_database)

           etName = findViewById(R.id.etDBName)
           etTaboo1 = findViewById(R.id.etDBtaboo1)
           etTaboo2 = findViewById(R.id.etDBtaboo2)
           etTaboo3 = findViewById(R.id.etDBtaboo3)
           btAdd = findViewById(R.id.btDBAdd)
        btAdd.setOnClickListener {
            name = etName.text.toString()
            tab1 = etTaboo1.text.toString()
            tab2 = etTaboo2.text.toString()
            tab3 = etTaboo3.text.toString()

            var database = HeadsUpDB(this)
            var status = database.savedat(name, tab1, tab2, tab3)
            Toast.makeText(
                applicationContext,
                "data saved successfully! " + status,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}