package com.example.headsupprep

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.view.MenuItemCompat



class MainActivity : AppCompatActivity() {
    private lateinit var myRv: RecyclerView
    private lateinit var searchView:SearchView
    private lateinit var rvAdapter: RVAdapter
    private lateinit var celebritybList: Celebrities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    }
    private fun searchCelebrities(name:String):CelebritiesItem? {
        var cel:CelebritiesItem?=null
        for (i in 0 until celebritybList.size) {
            if (celebritybList[i].name == name) {
                return celebritybList[i]
            }
        }
        return cel
    }
    private fun createApiInterface() {
        //show progress Dialog
        val progressDialog = ProgressDialog(this@MainActivity)
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
            rvAdapter=RVAdapter(list,this@MainActivity)
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
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

            R.id.refresh.apply{
                createApiInterface()
                return true
            }
        return super.onOptionsItemSelected(item)
    }

}
