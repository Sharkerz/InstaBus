package com.example.instabus.ui.List

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instabus.*

class ListFragment : Fragment(), OnStationClickListener {

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
   recyclerView.adapter = StationAdapter(listStation, this)

    return root
  }

    fun getDataStation() {
    val activity: MainActivity? = activity as MainActivity?
    listStation = activity?.getStationList()!!
  }

    override fun onItemClick(item: Station, position: Int) {
        val intent = Intent(requireActivity(), StationDetailActivity::class.java)
        intent.putExtra("street_name", item.street_name)
        intent.putExtra("id",item.id.toString())
        startActivity(intent)
    }

}