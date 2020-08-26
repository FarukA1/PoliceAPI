package com.example.policecrimerecords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.policecrimerecords.CustomClasses.Data
import com.example.policecrimerecords.CustomClasses.Location
import com.example.policecrimerecords.CustomClasses.Street
import kotlinx.android.synthetic.main.activity_may_report.*
import org.json.JSONArray
import org.json.JSONObject

class MayReport : AppCompatActivity() {

    lateinit var adapter : RecyclerAdapter

    private lateinit var layoutManager : RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_may_report)
        layoutManager = LinearLayoutManager(this)


        mayShoppingList.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        mayShoppingList.adapter = adapter

        getReport()
    }

    fun getReport() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
//        val url: String = "https://api.github.com/search/users?q=eyehunt"
        val url: String = "https://data.police.uk/api/crimes-at-location?date=2018-05&lat=52.629729&lng=-1.131592"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->


                var strResp = response.toString()
//                val jsonObj: JSONObject = JSONObject(strResp)
                val jsonArray: JSONArray = JSONArray(strResp)
                var str_user: String = ""
                for (i in 0 until jsonArray.length()) {
                    var jsonInner: JSONObject = jsonArray.getJSONObject(i)


                    val locationJsonObject : JSONObject = jsonInner.getJSONObject("location")
                   // val latitude = locationJsonObject.getString("latitude")
                   // val longitude = locationJsonObject.getString("longitude")
//                    val outcomeStatusObject : JSONObject = jsonInner.getJSONObject("outcome_status")

                    val streetJsonObject : JSONObject = locationJsonObject.getJSONObject("street")
                    //val streetName = streetJsonObject.getString("name")
//                    val verdict = outcomeStatusObject.getString("category")


//
//                    var outcome = OutcomeStatus(category = outcomeStatusObject.getString("category"))
                    var street = Street(name = streetJsonObject.getString("name"))
                    var location = Location(latitude = locationJsonObject.getString("latitude"),longitude = locationJsonObject.getString("longitude"),street = street)
                    var data = Data(category = jsonInner.getString("category"),location = location)
                    str_user = str_user + "\n" + "   Crime Offence"+ "\n" +  "   " + data.category  + "\n" +"   Location: " + data.location + "\n"
                }
//                report!!.text = "$str_user "
                adapter.data.add("$str_user")
                adapter.notifyDataSetChanged()

//                report.text = "${response.substring(0, 500)}"
            },
            Response.ErrorListener {adapter.data.add("There is no crime report from your location!")
                adapter.notifyDataSetChanged() })
        queue.add(stringReq)
    }

}
