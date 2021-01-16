package com.example.instabus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var listStation: List<Station>
    private val url = "http://barcelonaapi.marcpous.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(StationService::class.java)

        val StationRequest = service.listStations()

        StationRequest.enqueue(object : Callback<Stations> {
            override fun onResponse(call: Call<Stations>, response: Response<Stations>) {
                val allStations = response.body()
                if (allStations != null) {
                    listStation = allStations.data.tmbs
                }

                setContentView(R.layout.activity_main)

                //
                val navView: BottomNavigationView = findViewById(R.id.nav_view)

                val navController = findNavController(R.id.nav_host_fragment)
                val appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.navigation_list, R.id.navigation_map
                    )
                )
                setupActionBarWithNavController(navController, appBarConfiguration)
                navView.setupWithNavController(navController)
            }

            override fun onFailure(call: Call<Stations>, t: Throwable) {
                error("error")
            }
        })

    }

    fun getStationList(): List<Station> {
        return listStation
    }

}