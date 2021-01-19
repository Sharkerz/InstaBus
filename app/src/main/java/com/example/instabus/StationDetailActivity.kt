package com.example.instabus

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.instabus.databinding.ActivityStationDetailBinding

class StationDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityStationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_station_detail)

        val street_name = getIntent().getStringExtra("street_name")
        val id = getIntent().getStringExtra("id")

        binding.streetName.setText(street_name)
        binding.id.setText(id)
    }
}
