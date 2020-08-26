package com.example.policecrimerecords.CustomClasses


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Street(
    @SerializedName("name")
    val name: String
): Serializable{
    override fun toString(): String {
        return "   " + "Address: " + name
    }
}