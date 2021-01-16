package com.example.instabus.ui.List

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instabus.*

class ListFragment : Fragment() {

  private lateinit var listViewModel: ListViewModel;
  private lateinit var recyclerView: RecyclerView;
  private lateinit var listStation: List<Station>;

   override fun onCreateView(
       inflater: LayoutInflater,
       container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View? {

    listViewModel =
            ViewModelProvider(this).get(ListViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_list, container, false)


   getDataStation()

   recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view_list)
   recyclerView.layoutManager = LinearLayoutManager(activity)
   recyclerView.adapter = StationAdapter(listStation)

    return root
  }

    fun getDataStation() {
    val activity: MainActivity? = activity as MainActivity?
    listStation = activity?.getStationList()!!
  }

}