package com.example.headsuppgame

import android.util.Log
import com.example.headsuppgame.model.CelebritiesItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL

class NameChecker(val cel: CelebritiesItem) {

        //https://check-name.herokuapp.com/verify I used this api to check if the name is a real person name
         fun checkData():Int {
            var check=0
            CoroutineScope(Dispatchers.IO).launch {

                val data = async {

                    getData(cel.name!!)
                }.await()

                if (data.isNotEmpty()) {
                    Log.d("KeYCheck","not empty data  ")
                    check = checkName(data)
                }
            }
            return check
        }

    //if score is 0 it means it is not a real name
       fun checkName(data: String):Int {
            var score=0
            val jsonObj = JSONObject(data)
            score = jsonObj.getInt("score")
        Log.d("KeYCheck","here in get in check name and the score is $score  ")
            return score
        }

    fun getData(name:String):String {
          var data=""
        try {
            data = URL("https://check-name.herokuapp.com/verify/$name/")
                .readText(Charsets.UTF_8)
            Log.d("KeYCheck","here in get data to check name  ")
        }catch (e: Exception){
            println("Error: $e")
        }
        return data
    }

}