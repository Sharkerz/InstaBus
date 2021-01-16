package com.example.instabus

import android.app.Service
import android.content.Intent
import android.os.IBinder
import retrofit2.Call
import retrofit2.http.GET


interface StationService {
    @GET("/bus/stations.json")
    fun listStations(): Call<Stations>
}
