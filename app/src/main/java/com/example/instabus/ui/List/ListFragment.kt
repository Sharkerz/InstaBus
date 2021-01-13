package com.example.instabus.ui.List

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instabus.R
import com.example.instabus.StationAdapter
import com.example.instabus.StationItem

class ListFragment : Fragment() {

  private lateinit var listViewModel: ListViewModel;
  private lateinit var recyclerView: RecyclerView;

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    listViewModel =
            ViewModelProvider(this).get(ListViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_list, container, false)
    //val textView: TextView = root.findViewById(R.id.text_list)
//    listViewModel.text.observe(viewLifecycleOwner, Observer {
//      textView.text = it
//    })

        val StationList = generateDummyList(10)

    recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view_list)
    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.adapter = StationAdapter(StationList)

//        recycler_view = root.findViewById<RecyclerView>(R.id.recycler_view_list)
//        recycler_view.adapter = StationAdapter(StationList)
//        recycler_view.layoutManager = LinearLayoutManager(this)
//        recycler_view.setHasFixedSize(true)

    return root
  }



      private fun generateDummyList(size: Int): List<StationItem> {
        val list = ArrayList<StationItem>()

        for (i in 0 until size) {
          val item = StationItem("Item $i")
          list += item
        }
        return list
      }

}