package com.example.instabus

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

data class Station(
    val id: Int,
    val street_name: String,
    val utm_x: String,
    val utm_y: String,
    val lat: String,
    val lon: String
)

data class Stations(
    @SerializedName("data") val data: StationList
)

data class StationList(
    @SerializedName("tmbs") val tmbs: List<Station>
)