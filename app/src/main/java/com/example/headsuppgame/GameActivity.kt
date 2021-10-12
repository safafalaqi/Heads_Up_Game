package com.example.headsuppgame

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.core.view.isVisible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GameActivity : AppCompatActivity() {
    lateinit var celebrities:Celebrities
    lateinit var celebritiesUnique:Celebrities
    lateinit var celebrity:CelebritiesItem

    var counter=0
    var startGame=false
    var isSetTime=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //hide actionbar
        supportActionBar?.hide()

        val start=findViewById<Button>(R.id.btStart)
        val back=findViewById<Button>(R.id.btBackGame)
        //we get call api
        createApiInterface()

    }
    //to control orientation
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(newConfig!=null) {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {

                    setTimer()
                    setVisibility(false,true,true)
                    getUniqueCelebrity(counter)
                    counter++
            } else if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){

                    setVisibility(true,true,false)

            }
        }
    }

    fun setVisibility(tvRotate:Boolean,llFirst:Boolean,llSecond:Boolean){
        findViewById<TextView>(R.id.tvRotate).isVisible=tvRotate
        findViewById<LinearLayout>(R.id.llFirst).isVisible=llFirst
        findViewById<LinearLayout>(R.id.llsecond).isVisible=llSecond
    }

    fun getUniqueCelebrity(n:Int){
        if(n<celebrities.size) {
            celebrity = celebrities.get(n)
            findViewById<TextView>(R.id.tvnameGame).text = celebrity.name
            findViewById<TextView>(R.id.tvTabooGame1).text = celebrity.taboo1
            findViewById<TextView>(R.id.tvTabooGame2).text = celebrity.taboo2
            findViewById<TextView>(R.id.tvTabooGame3).text = celebrity.taboo3
        }
        else
            counter=0
    }

    //set timer for every round
    fun setTimer() {
        if(!startGame){
            startGame=true
        //this part to display a timer for 60 seconds
            object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    findViewById<TextView>(R.id.tvTimer).setText("Time: ${millisUntilFinished / 1000 }")
                }
                override fun onFinish() {
                    findViewById<TextView>(R.id.tvTimer).setText("Time: --")
                    startGame=false
                    val intent = Intent(this@GameActivity, StartActivity::class.java)
                    startActivity(intent)
                }
            }.start()
    }}


    fun createApiInterface() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<Celebrities?>? = apiInterface!!.getUsersInfo()
        call?.enqueue(object : Callback<Celebrities?> {
            override fun onResponse(
                call: Call<Celebrities?>?,
                response: Response<Celebrities?>
            ) {
                celebrities=response.body()!!

            }
            override fun onFailure(call: Call<Celebrities?>, t: Throwable?) {
                Toast.makeText(applicationContext,"Unable to load data!", Toast.LENGTH_SHORT).show()
                call.cancel()
            }
        })
    }
 }
