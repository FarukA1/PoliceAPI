package com.example.policecrimerecords

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    lateinit var sharePreference : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //sharePreference for dark mode
    sharePreference = PreferenceManager.getDefaultSharedPreferences(this)
        sharePreference.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
        val activeDarkMode = sharePreference.getBoolean("hide_single_count",false)
        val nowDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        if(activeDarkMode != nowDarkMode){
            nightMode(activeDarkMode)
        }

        // content of listView for the main activity
        val values = arrayListOf("Select month to get result from 2018", "January","February","March","April","May","Get report from your location")

        val listView = findViewById<ListView>(R.id.listview)

        // add function to the items in the listView
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            if (position==0){
                Toast.makeText(this@MainActivity, "Select month..",Toast.LENGTH_SHORT).show()
            }
            if(position==1) {

                val intent = Intent(this, JanuaryReport::class.java)
                startActivity(intent)
            }
                if(position==2){

                val intent = Intent(this,FebruaryReport::class.java)
                startActivity(intent)
            }
            if(position==3){

                val intent = Intent(this,MarchReport::class.java)
                startActivity(intent)
            }
            if(position==4){

                val intent = Intent(this,AprilReport::class.java)
                startActivity(intent)
            }
            if(position==5){
                    
                val intent = Intent(this,MayReport::class.java)
                startActivity(intent)
            }
            if(position==6){
                Toast.makeText(this@MainActivity, "Customise Report..",Toast.LENGTH_SHORT).show()

                val intent = Intent(this,CustomiseReport::class.java)
                startActivity(intent)
            }
        }
    }

    // activating the menu option
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }


    // adding function to the item in menu option
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.setting){
            val intent = Intent(this,SettingActivity::class.java )
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    //dark mode
    private var sharedPreferencesListener = SharedPreferences.OnSharedPreferenceChangeListener{ sharedPreferences, s ->

        if(s=="hide_single_count"){
            val mode = sharedPreferences.getBoolean(s,false)
            nightMode(mode)
        }
    }

    private fun nightMode(s : Boolean){
        if (s){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            onRestart()
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            onRestart()
        }
    }


}
