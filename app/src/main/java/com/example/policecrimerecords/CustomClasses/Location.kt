package com.example.policecrimerecords.CustomClasses


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("street")
    val street: Street
):Serializable{
    override fun toString(): String {
        return latitude + "," + longitude + "\n" + street
    }
}