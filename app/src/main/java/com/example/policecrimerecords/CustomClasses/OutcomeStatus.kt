package com.example.policecrimerecords.CustomClasses


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OutcomeStatus(
    @SerializedName("category")
    val category: String
):Serializable{
    override fun toString(): String {
        return category
    }
}