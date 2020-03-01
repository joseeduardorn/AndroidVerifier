package com.example.androidverifier

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
//https://stackoverflow.com/questions/29153255/send-information-from-android-application-to-a-web-service-and-back
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // setSupportActionBar(toolbar)
/*
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        } */

        idShowDeviceName.text =  Verify.getDeviceName()//" Value "+Verify.isUSBMassStorageEnabled(this);
        idShowVersion.text = Verify.getVersionAndSKD()

        if(  Verify.getVersion() != "9" && Verify.getVersion() != "10" ){
            txtemail.isEnabled = false
            id_btn_verify.isEnabled = false
            //Toast.makeText(this,"Solo Android 9(Pie) disponible para evaluar",Toast.LENGTH_LONG).show()
            MessageBuilder.makeToastLengthLong(this, "Solo version Android 9(Pie) o 10 disponible para evaluar "+Verify.getVersion())
        }

        if(!Verify.isNetworkAvailable(this)){
            //Toast.makeText(getActivity(), "This is my Toast message!", Toast.LENGTH_LONG).show();
            //Toast.makeText(this,"Internet no disponible",Toast.LENGTH_LONG).show()
            MessageBuilder.makeToastLengthLong(this, "Internet no disponible")
            id_btn_verify.isEnabled = false
        }

        id_btn_verify.setOnClickListener{ view ->
            //to do

            if(txtemail.text.toString().isEmpty() ){
                MessageBuilder.makeToastLengthLong(this, "Necesita ingresar un correo")
                return@setOnClickListener;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(txtemail.text.toString()).matches() ){
                MessageBuilder.makeToastLengthLong(this, "Dirección de correo no validad")
                return@setOnClickListener;
            }
            val apiSIn = ApiService.getRetrofitBuilder().create(ApiInterface::class.java)
            val jsonBuilder = JSONBuilder();
            //jsonBuilder.addJsonObject("email_user", txtemail.text.toString())
           Log.d("json builder",
               jsonBuilder.makeJSONVerifyBody(this, txtemail.text.toString() ).toString()
           )
            val configObject = ConfigObject(this, txtemail.text.toString())

           // val callProcess : Call<JsonElement>? = apiSIn.getProcess(jsonBuilder.makeJSONVerifyBody(this,txtemail.text.toString()))//configObject.getConfigObject())//(jsonBuilder.makeJSONVerifyBody(this))
            val callProcess : Call<JsonElement>? = apiSIn.getProcess(configObject)

            callProcess?.enqueue(object : Callback<JsonElement>{
                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Log.d("json failure",t.toString())
                }

                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    Log.d("response body",response.body().toString());
                    MessageBuilder.makeToastLengthLong(this@MainActivity, "Error inesperado")
                    //Log.d("response raw",response.raw().toString());
                    Log.d("response message",response.message().toString());
                    if(response.isSuccessful){
                        MessageBuilder.makeToastLengthLong(this@MainActivity, "Se ha enviado un correo con la evaluación")
                    }

                }
            })


        /*
            val callGet : Call<JsonElement>? = apiSIn.getapi()

            callGet?.enqueue(object : Callback<JsonElement>{
                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    Log.d("json failure",t.toString())
                }

                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    // val to: String? = Gson().toJson(response.body())

                    //Log.d("TAG Request", call.request().toString().toString() )
                    Log.d("TAG Response Code ", response.body().toString() )

                }
            }) */

        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
