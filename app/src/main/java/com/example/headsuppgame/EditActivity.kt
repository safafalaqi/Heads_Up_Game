package com.example.headsuppgame

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL
import java.util.*


class EditActivity : AppCompatActivity() {
    private lateinit var myRv: RecyclerView
    private lateinit var searchView:SearchView
    private lateinit var rvAdapter: RVAdapter
    var celebritybList= Celebrities()
    var celebrityFilteredbList=celebritybList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        getSupportActionBar()?.setTitle("Celebrities List")

        //declare UI elements in main activity
        val addButton = findViewById<Button>(R.id.btAddCel)
        val submitButton = findViewById<Button>(R.id.btSubmit)
        val name = findViewById<EditText>(R.id.etCelName)
        myRv = findViewById(R.id.rvCel)
        createApiInterface()

        addButton.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
        submitButton.setOnClickListener{
            val intent = Intent(this, UpdateDeleteActivity::class.java)
            val celebrity:CelebritiesItem?=searchCelebrities(name.text.toString())
            if(celebrity!=null){
            intent.putExtra("celebrity",celebrity)
            startActivity(intent)
            }
            else
            Toast.makeText(this,"Celebrity not found!", Toast.LENGTH_SHORT).show()
        }
        //val filter = findViewById<FloatingActionButton>(R.id.fbFilter)

    }

     fun searchCelebrities(name:String):CelebritiesItem? {
        var cel:CelebritiesItem?=null
        for (i in 0 until celebritybList.size) {
            if (celebritybList[i].name == name) {
                return celebritybList[i]
            }
        }
        return cel
    }
    fun createApiInterface() {
        //show progress Dialog
        val progressDialog = ProgressDialog(this@EditActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        val call: Call<Celebrities?>? = apiInterface!!.getUsersInfo()

        call?.enqueue(object : Callback<Celebrities?> {
            override fun onResponse(
                call: Call<Celebrities?>?,
                response: Response<Celebrities?>
            ) {
                progressDialog.dismiss()

                declareMyRv(response.body()!!)
                celebritybList=response.body()!!

                //to scroll to the bottom of the recycler view
                myRv.scrollToPosition( rvAdapter.getItemCount() - 1)

            }
            override fun onFailure(call: Call<Celebrities?>, t: Throwable?) {
                Toast.makeText(applicationContext,"Unable to load data!", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
                call.cancel()
            }
        })
    }
    fun declareMyRv(list:Celebrities) {
        if(myRv.adapter==null)
        {
            //Log.d("checkdiff","declare my rv for the first time")
            rvAdapter=RVAdapter(list,this@EditActivity)
            myRv.adapter = rvAdapter
            myRv.layoutManager = LinearLayoutManager(applicationContext)
        }
        else
        {
            //this for DiffUtil
            rvAdapter.updateList(list)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu?.findItem(R.id.action_search)
        if(celebritybList!=null){

        if (menuItem != null) {
            val searchItem = menuItem.actionView as SearchView
            searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        celebrityFilteredbList.clear()
                        val search = newText!!.toLowerCase(Locale.getDefault())
                        celebritybList.forEach {
                            if (it.name?.toLowerCase(Locale.getDefault()).toString()
                                    .contains(search)
                            ) {
                                celebrityFilteredbList.add(it)
                            }
                        }
                       // myRv.adapter!!.notifyDataSetChanged()
                        rvAdapter.updateList(celebrityFilteredbList)
                    } else {
                        celebrityFilteredbList.clear()
                        celebrityFilteredbList.addAll(celebritybList)
                        rvAdapter.updateList(celebrityFilteredbList)
                        //myRv.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }
            })}
        }
        return true
            }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

            R.id.refresh.apply{
                createApiInterface()
                return true
            }
        return super.onOptionsItemSelected(item)
    }
    /*
    fun  filterList(){
    val celFiltered=celebritybList

    if (celebritybList != null)
    {
        celFiltered.clear()
        for (i in celebritybList) {
            var score = filterByName(i.name!!)
            if (score == 0) {
            } else
                celFiltered.add(i)
        }
        rvAdapter.updateList(celFiltered)
    }
    }*/
/*
    fun filterByName(name:String):Int {
        var score = 0
        CoroutineScope(Dispatchers.IO).launch {
            var data = ""
            try {
                data = URL("https://check-name.herokuapp.com/verify/$name/")
                    .readText(Charsets.UTF_8)
                Log.d("KeYCheck", "here in get data to check name  ")
            } catch (e: Exception) {
                println("Error: $e")
            }
            val jsonObj = JSONObject(data)
            score = jsonObj.getInt("score")
            Log.d("KeYCheck", "here in get in check name and the score is $score  ")
        }.wait()
        return score

    }*/


}
