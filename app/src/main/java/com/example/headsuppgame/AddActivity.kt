package com.example.headsuppgame

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        getSupportActionBar()?.setTitle("Add A Celebrity")
        val name=findViewById<EditText>(R.id.etNameAddActiv)
        val taboo1=findViewById<EditText>(R.id.etTaboo1)
        val taboo2=findViewById<EditText>(R.id.etTaboo2)
        val taboo3=findViewById<EditText>(R.id.etTaboo3)
        val addbt=findViewById<Button>(R.id.btAddCelActiv)
        val back=findViewById<Button>(R.id.btBack)

        addbt.setOnClickListener{
            //get user name and location from edit text
            addCelebrity(name.text.toString(),taboo1.text.toString(),taboo2.text.toString(),taboo3.text.toString())

        }
        back.setOnClickListener{
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addCelebrity(name: String, taboo1: String, taboo2: String, taboo3: String) {
        //show progress Dialog
        val progressDialog = ProgressDialog(this@AddActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        //check if user inputs are not empty
        if(name.isNotEmpty()&& taboo1.isNotEmpty()&& taboo2.isNotEmpty()&& taboo3.isNotEmpty()) {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            val user = CelebritiesItem(name,taboo1,taboo2,taboo3)
            apiInterface!!.addUsersInfo(user).enqueue(object : Callback<CelebritiesItem?> {
                override fun onResponse(
                    call: Call<CelebritiesItem?>?,
                    response: Response<CelebritiesItem?>
                ) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Add Success!", Toast.LENGTH_SHORT).show();
                }
                override fun onFailure(call: Call<CelebritiesItem?>, t: Throwable) {
                    Toast.makeText(applicationContext, "Unable to add celebrity", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                    call.cancel()
                }
            })
        }
        else {
            Toast.makeText(applicationContext, "Enter name and 3 taboos", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }
}