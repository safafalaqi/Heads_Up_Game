package com.example.headsuppgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //hide actionbar
        supportActionBar?.hide()
        val btStart=findViewById<Button>(R.id.btStartGame)
        val btEdit=findViewById<Button>(R.id.btEditCel)

        btStart.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
            val intent = Intent(this@MainActivity, StartActivity::class.java)
            startActivity(intent)
            }
        }
        btEdit.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val intent = Intent(this@MainActivity, EditActivity::class.java)
                startActivity(intent)
            }
        }
    }

}