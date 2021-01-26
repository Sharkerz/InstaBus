package com.example.instabus

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instabus.DBHelper.DBHelper
import com.example.instabus.databinding.ActivityStationDetailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.station_item.view.*


class StationDetailActivity : AppCompatActivity(){
    lateinit var binding: ActivityStationDetailBinding
    lateinit var dbHelper: DBHelper
    private lateinit var recyclerView: RecyclerView;
    private var adapter: PictureAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_station_detail)

        var listPicture : MutableList<Picture> = mutableListOf()
        val street_name = getIntent().getStringExtra("street_name")
        val id = getIntent().getStringExtra("id")


        // showing the back button in action bar
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        binding.stationName.setText(street_name)

        //Display pictures
        dbHelper = DBHelper(this)
        var db = dbHelper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM PICTURE WHERE ID_station = $id", null)

        while (rs.moveToNext()) {
            var picture = Picture(rs.getInt(0), rs.getString(1), rs.getBlob(2))
            listPicture.add(picture)
        }

        recyclerView = findViewById(R.id.recycler_picture_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PictureAdapter(listPicture)

//        var itemTouchHelper = ItemTouchHelper(SwipeToDelete(recyclerView.adapter as PictureAdapter))
//        itemTouchHelper.attachToRecyclerView(recyclerView)

        //Button take picture
        val button = findViewById<FloatingActionButton>(R.id.addPictureButton)
        button.setOnClickListener {
            val intent = Intent(this, TakePictureActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("street_name", street_name)
            startActivity(intent)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter = recyclerView.adapter as PictureAdapter
                var pos = viewHolder.adapterPosition
                if (adapter != null) {
                    adapter!!.deleteItem(pos)
                    var item_id = viewHolder.itemView.tag

                    //delete in database
                    var db_itemDelete = dbHelper.writableDatabase
                    var rs_itemDelete = db_itemDelete.rawQuery("DELETE FROM PICTURE WHERE ID = $item_id", null)
                    rs_itemDelete.moveToFirst()
                }
            }
        }).attachToRecyclerView(recyclerView)

    }

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.getItemId()) {
        android.R.id.home -> {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            return true
        }
    }
    return super.onOptionsItemSelected(item)
}


}
