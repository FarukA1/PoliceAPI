package com.example.policecrimerecords.CustomClasses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class StopClass (
    @SerializedName("age_range")
    val age_range: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("legislation")
    val legislation: String,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("object_of_search")
    val object_of_search: String
    ): Serializable {
    override fun toString(): String {
        return age_range + "\n" + gender + "\n" + legislation + "\n" + datetime + object_of_search
    }
}






