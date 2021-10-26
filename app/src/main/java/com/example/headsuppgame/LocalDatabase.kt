package com.example.headsuppgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.headsuppgame.adapter.RVAdapter
import com.example.headsuppgame.database.HeadsUpDB
import com.example.headsuppgame.model.Celebrities
import com.example.headsuppgame.model.CelebritiesItem

class LocalDatabase : AppCompatActivity() {
    private lateinit var myRV: RecyclerView
    private lateinit var rvAdapter: RVAdapter
    lateinit var etName: EditText
    lateinit var etTaboo1: EditText
    lateinit var etTaboo2: EditText
    lateinit var etTaboo3: EditText
    lateinit var btAdd: Button

    lateinit var celebrities: Celebrities

    var name= ""
    var tab1= ""
    var tab2= ""
    var tab3= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_database)


        if(HeadsUpDB(this).readData()!=null){
            celebrities=HeadsUpDB(this).readData()
            setRV()
        }

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



            if(name.isNotBlank()&&tab1.isNotBlank()&&tab2.isNotBlank()&&tab3.isNotBlank()) {
                var database = HeadsUpDB(this)
                var status = database.saveData(CelebritiesItem(name, tab1, tab2, tab3))
                Toast.makeText(
                    applicationContext,
                    "data saved successfully! " + status,
                    Toast.LENGTH_SHORT
                ).show()
                celebrities=database.readData()
                setRV()
            }else
                Toast.makeText(
                    applicationContext,
                    "Fields can not be empty! ",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
    private fun setRV() {
        myRV=findViewById(R.id.rvCElDatabase)
        rvAdapter = RVAdapter(celebrities,this,1)
        myRV.adapter = rvAdapter
        myRV.layoutManager = LinearLayoutManager(applicationContext)
    }
}