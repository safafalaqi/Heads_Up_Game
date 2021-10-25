package com.example.headsuppgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.*
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        //hide actionbar
        supportActionBar?.hide()

        val start = findViewById<Button>(R.id.btStart)
        val back = findViewById<Button>(R.id.btBackGame)

        //user decide from where we get data either API or Database
        val dataResources = resources.getStringArray(R.array.DataResources)
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, dataResources  )
            spinner.adapter = adapter
        }

            start.setOnClickListener {
            setVisibility(true,false)
            object : CountDownTimer(4000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    findViewById<TextView>(R.id.tvThreeC).setText("${millisUntilFinished / 1000}")
                }

                override fun onFinish() {
                    CoroutineScope(Dispatchers.IO).launch {
                        val intent = Intent(this@StartActivity, GameActivity::class.java)
                        intent.putExtra("dataSource",spinner.selectedItem.toString())
                        startActivity(intent)
                    }
                }
            }.start()
        }
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun setVisibility(a:Boolean,b:Boolean){
        findViewById<TextView>(R.id.tvThreeC).isVisible=a
        findViewById<LinearLayout>(R.id.llStart).isVisible=b
    }


}
