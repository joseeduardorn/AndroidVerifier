package com.example.androidverifier

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
//https://stackoverflow.com/questions/29153255/send-information-from-android-application-to-a-web-service-and-back
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


        idShowDeviceName.text =  Verify.getDeviceName()//" Value "+Verify.isUSBMassStorageEnabled(this);
        idShowVersion.text = Verify.getVersionAndSKD()

        if(Verify.getVersion() != "9" ){
            txtemail.isEnabled = false
            id_btn_verify.isEnabled = false
            Toast.makeText(this,"Solo Android 9(Pie) disponible para evaluar",Toast.LENGTH_LONG).show()
        }

        if(!Verify.isNetworkAvailable(this)){
            //Toast.makeText(getActivity(), "This is my Toast message!", Toast.LENGTH_LONG).show();
            Toast.makeText(this,"Internet no disponible",Toast.LENGTH_LONG).show()
            id_btn_verify.isEnabled = false
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
