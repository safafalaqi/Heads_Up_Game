package com.example.headsupprep

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDeleteActivity : AppCompatActivity() {
    lateinit var  name:EditText
    lateinit var taboo1:EditText
    lateinit var taboo2:EditText
    lateinit var taboo3:EditText
    lateinit var delete:Button
    lateinit var update:Button
    lateinit var back:Button

    lateinit var celebrity:CelebritiesItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)

        name=findViewById(R.id.etName)
        taboo1=findViewById(R.id.etTaboo1UD)
        taboo2=findViewById(R.id.etTaboo2UD)
        taboo3=findViewById(R.id.etTaboo3UD)
        delete=findViewById(R.id.btDelCelActiv)
        update=findViewById(R.id.btUpCelActiv)
        back=findViewById(R.id.btBackUD)

        //get data from previous activity
        if (intent.extras != null) {
            celebrity = intent.getSerializableExtra("celebrity") as CelebritiesItem
        }

        name.setText(celebrity.name)
        taboo1.setText(celebrity.taboo1)
        taboo2.setText(celebrity.taboo2)
        taboo3.setText(celebrity.taboo3)

        update.setOnClickListener{
            //get user name and taboos
           updateCelebrity(celebrity.pk,name.text.toString(),taboo1.text.toString(),taboo2.text.toString(),taboo3.text.toString())
        }
        delete.setOnClickListener{
            alertDialog()
        }

        back.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private fun updateCelebrity(
        id: Int?,
        name: String,
        taboo1: String,
        taboo2: String,
        taboo3: String
    ) {
        //show progress Dialog
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        //check if user inputs are not empty
        if (name.isNotEmpty() && taboo1.isNotEmpty()&&taboo2.isNotEmpty()&&taboo3.isNotEmpty()) {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            if (id != null) {
                apiInterface!!.updateUsersInfo(id.toInt(),CelebritiesItem(name, taboo1,taboo2,taboo3)).enqueue(object :
                    Callback<CelebritiesItem?> {
                    override fun onResponse(
                        call: Call<CelebritiesItem?>?,
                        response: Response<CelebritiesItem?>
                    ) {
                        progressDialog.dismiss()
                        Toast.makeText(this@UpdateDeleteActivity, "Update Success!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<CelebritiesItem?>, t: Throwable) {
                        Toast.makeText(this@UpdateDeleteActivity, "Unable to update user", Toast.LENGTH_SHORT)
                            .show()
                        progressDialog.dismiss()
                        call.cancel()
                    }
                })
            }
        } else {
            Toast.makeText(this@UpdateDeleteActivity, "Enter name and 3 taboos", Toast.LENGTH_SHORT)
                .show()
            progressDialog.dismiss()
        }
    }

    private fun deleteCelebrity(celebrity: CelebritiesItem) {
        //show progress Dialog
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface!!.deleteUsersInfo(celebrity.pk!!).enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>?,
                response: Response<Void>
            ) {
                progressDialog.dismiss()
                Toast.makeText(this@UpdateDeleteActivity, "Delete Success!", Toast.LENGTH_SHORT)
                    .show()
                name.setEnabled(false)
                taboo1.setEnabled(false)
                taboo2.setEnabled(false)
                taboo3.setEnabled(false)
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@UpdateDeleteActivity, "Unable to delete user", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
                call.cancel() }
             })

    }
    //to hide the keyboard
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun alertDialog(){

        // first we create a variable to hold an AlertDialog builder
        val dialogBuilder = AlertDialog.Builder(this)
        // here we set the message of our alert dialog
        dialogBuilder.setMessage("Are you sure you want to delete this celebrity?")
        dialogBuilder.setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id ->
            deleteCelebrity(celebrity)
            })
            // negative button text and action
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        // show alert dialog
        alert.show()
    }

}