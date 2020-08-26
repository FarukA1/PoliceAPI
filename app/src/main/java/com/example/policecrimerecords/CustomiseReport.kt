package com.example.policecrimerecords

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.policecrimerecords.CustomClasses.Data
import com.example.policecrimerecords.CustomClasses.OutcomeStatus
import com.example.policecrimerecords.CustomClasses.Street
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_customise_report.*
import org.json.JSONArray
import org.json.JSONObject

class CustomiseReport : AppCompatActivity() {

    val RequestPermissionCode = 1
    var mLocation: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var notifier: Notifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customise_report)

        //getLastLocation()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        notifier = Notifier(this)

    }


    fun reportPressed(view: View) {
            getReport()
    }

    fun getReport() {


        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "https://data.police.uk/api/crimes-at-location?date=2018-01&lat=${editTextLatitude.text}&lng=${editTextLongitude.text}"
        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->


                var strResp = response.toString()
//                val jsonObj: JSONObject = JSONObject(strResp)
                val jsonArray: JSONArray = JSONArray(strResp)
                var str_user: String = ""
                for (i in 0 until jsonArray.length()) {
                    var jsonInner: JSONObject = jsonArray.getJSONObject(i)


                    val locationJsonObject : JSONObject = jsonInner.getJSONObject("location")
                    val outcomeStatusObject : JSONObject = jsonInner.getJSONObject("outcome_status")
                 //   val latitude = locationJsonObject.getString("latitude")
                   // val longitude = locationJsonObject.getString("longitude")
                    val streetJsonObject : JSONObject = locationJsonObject.getJSONObject("street")
                   // val streetName = streetJsonObject.getString("name")
                   // val verdict = outcomeStatusObject.getString("category")

                    var outcome = OutcomeStatus(category = outcomeStatusObject.getString("category"))
                    var street = Street(name = streetJsonObject.getString("name"))
                    var location = com.example.policecrimerecords.CustomClasses.Location(
                        latitude = locationJsonObject.getString("latitude"),
                        longitude = locationJsonObject.getString("longitude"),
                        street = street
                    )
                    var data = Data(category = jsonInner.getString("category"),location = location)
                    str_user = str_user + "\n" + "   Crime Offence"+ "\n" +  "   " + data.category  + "\n" +"   Location: " + data.location + "\n" + "   " + "Verdict: " + outcome + "\n"
                }
                result!!.text = "$str_user "

//                report.text = "${response.substring(0, 500)}"
            },
            Response.ErrorListener { result!!.text = "That didn't work!" })
        queue.add(stringReq)
    }

    fun getLocation(view: View){
        getLastLocation()
    }


    fun getLastLocation(){

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )!=PackageManager.PERMISSION_GRANTED
        ){
            requestPermission()
        }else{
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location:Location ->
                    mLocation = location
                    if(location!=null){
                        val latitude = location.latitude.toString()
                        editTextLatitude.setText(latitude)
                        val longitude = location.longitude.toString()
                        editTextLongitude.setText(longitude)

                        val task : String = "Your current location is " + location.latitude.toString() + ", " + location.longitude.toString()
                        val map : String =".Click to view"
                        notifier.sendNotificatiion(task + "\n" + map)


                    }


                }
        }
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            RequestPermissionCode
        )
        this.recreate()
    }
}
