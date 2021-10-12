package com.example.headsuppgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
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

        start.setOnClickListener {
            setVisibility(true,false)
            object : CountDownTimer(4000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    findViewById<TextView>(R.id.tvThreeC).setText("${millisUntilFinished / 1000}")
                }

                override fun onFinish() {
                    CoroutineScope(Dispatchers.IO).launch {
                        val intent = Intent(this@StartActivity, GameActivity::class.java)
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
