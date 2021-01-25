package com.example.instabus

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.instabus.DBHelper.DBHelper
import com.example.instabus.Utils.Utils
import kotlinx.android.synthetic.main.activity_take_picture.*
import kotlinx.android.synthetic.main.station_item.imageView

private var REQUEST_CODE = 42

class TakePictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)
        val id = getIntent().getStringExtra("id")
        val street_name = getIntent().getStringExtra("street_name")

        checkPermissions()

        val button_sv = findViewById<Button>(R.id.button_save)
        //prevalid form for save image
        button_sv.setOnClickListener {
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val title = title_image.text.toString()

            if(!TextUtils.isEmpty(title_image.text.toString())) {
                DBHelper(applicationContext)
                    .addBitmap(id!!.toInt(), title, Utils.getBytes(bitmap))
                val intent = Intent(this, StationDetailActivity::class.java)
                intent.putExtra("id",id)
                intent.putExtra("street_name",street_name)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Please enter a title for picture", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun TakePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val REQUEST_IMAGE_CAPTURE = 1

        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val takeImage = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(takeImage)


        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun checkPermissions() {
        //Permissions take picture
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            TakePicture()
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            REQUEST_CODE -> {
                if (grantResults[0] != -1) {
                    TakePicture()
                }
                else {
                    Toast.makeText(this, "U had to accept the use of the camera", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}