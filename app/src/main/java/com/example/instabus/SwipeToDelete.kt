//package com.example.instabus
//
//import android.content.Context
//import android.view.View
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.RecyclerView
//import com.example.instabus.DBHelper.DBHelper
//import kotlin.coroutines.coroutineContext
//
//class SwipeToDelete(var adapter: PictureAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
//) {
//    internal lateinit var dbHelper: DBHelper
//    var activity = StationDetailActivity()
//
//    override fun onMove(
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        target: RecyclerView.ViewHolder
//    ): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        var pos = viewHolder.adapterPosition
//        adapter.deleteItem(pos)
//        var id = adapter.getId(pos)
//        activity.delete(id)
//
//        println(DBHelper)
//        //var db = dbHelper.
//        //var rs = db.rawQuery("SELECT * FROM PICTURE WHERE ID_station = $id", null)
//
//    }
//
//}