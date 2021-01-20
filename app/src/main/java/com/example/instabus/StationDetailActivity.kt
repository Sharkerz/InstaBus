package com.example.instabus

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instabus.DBHelper.DBHelper
import com.example.instabus.Utils.Utils
import com.example.instabus.databinding.ActivityStationDetailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.station_item.*
import com.example.instabus.Utils.*

class StationDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityStationDetailBinding
    internal lateinit var dbHelper: DBHelper
    private lateinit var recyclerView: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_station_detail)

        var listPicture : MutableList<Picture> = mutableListOf()

        val street_name = getIntent().getStringExtra("street_name")
        val id = getIntent().getStringExtra("id")

        binding.stationName.setText(street_name)

        //Display pictures
        dbHelper = DBHelper(this)
        var db = dbHelper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM PICTURE WHERE ID_station = $id", null)

        while (rs.moveToNext()) {
            var picture = Picture(rs.getString(1), rs.getBlob(2))
            listPicture.add(picture)
        }

        recyclerView = findViewById(R.id.recycler_picture_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PictureAdapter(listPicture)

//        val img = Utils.getImage(rs.getBlob(2))
//        val test = findViewById<ImageView>(R.id.test)
//        test.setImageBitmap(img)

        //Button take picture
        val button = findViewById<FloatingActionButton>(R.id.addPictureButton)
        button.setOnClickListener {
            val intent = Intent(this, TakePictureActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("street_name",street_name)
            startActivity(intent)
        }
    }


}
