package com.example.policecrimerecords.CustomClasses


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("category")
    val category: String,
    @SerializedName("location")
    val location: Location
):Serializable
    {

    override fun toString(): String {
        return category + "\n" + location
    }
}




