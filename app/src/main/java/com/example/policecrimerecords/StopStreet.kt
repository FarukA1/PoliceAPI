package com.example.policecrimerecords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.policecrimerecords.CustomClasses.*
import kotlinx.android.synthetic.main.activity_stop_street.*
import org.json.JSONArray
import org.json.JSONObject

class StopStreet : AppCompatActivity() {

    lateinit var adapter : RecyclerAdapter

    private lateinit var layoutManager : RecyclerView.LayoutManager

    // same lat and long from JanuaryActivity
    val latitude : String = "52.629729"
    val longitude : String = "-1.131592"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_street)

        layoutManager = LinearLayoutManager(this)

        stopShoppingList.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        stopShoppingList.adapter = adapter

        getReport()
    }

    fun getReport() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
//        val url: String = "https://api.github.com/search/users?q=eyehunt"
        val url: String = "https://data.police.uk/api/stops-street?lat=$latitude&lng=$longitude&date=2018-02"

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


                    var stopClass = StopClass(age_range = jsonInner.getString("age_range"),gender = jsonInner.getString("gender"),
                        legislation = jsonInner.getString("legislation"),datetime = jsonInner.getString("datetime"),object_of_search = jsonInner.getString("object_of_search"))

                    str_user = str_user + "\n" + "   Age range: " + stopClass.age_range + "\n" + "   Gender: " + stopClass.gender + "\n" + "   Legislation: " + stopClass.legislation +
                     "\n" + "  Time: " + stopClass.datetime + "\n" + "   Object of Search: " + stopClass.object_of_search + "\n"

                }

                adapter.data.add("$str_user")
                adapter.notifyDataSetChanged()

            },
            Response.ErrorListener {adapter.data.add("There is no crime report from your location!")
                adapter.notifyDataSetChanged() })
        queue.add(stringReq)


    }
}
