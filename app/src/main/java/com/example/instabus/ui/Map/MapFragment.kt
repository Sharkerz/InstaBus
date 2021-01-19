package com.example.instabus.ui.Map

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.instabus.MainActivity
import com.example.instabus.R
import com.example.instabus.Station
import com.example.instabus.StationDetailActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment(), OnMapReadyCallback,ClusterManager.OnClusterItemClickListener<mapItem>{
  companion object {
    private const val LOCATION_PERMISSION_REQUEST_CODE = 1
  }

  private lateinit var dashboardViewModel: MapViewModel
  private lateinit var googleMap : GoogleMap
  private val Barcelone = LatLng(41.405049, 2.163203)
  private lateinit var listStation: List<Station>;
  private lateinit var lastLocation: Location
  private lateinit var fusedLocationClient: FusedLocationProviderClient
  private lateinit var clusterManager: ClusterManager<mapItem>

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    map_view.onCreate(savedInstanceState)
    map_view.onResume()

    map_view.getMapAsync(this)
    getDataStation()
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
  }

  override fun onMapReady(map: GoogleMap?) {
    if(map != null){
      googleMap = map
    }
    googleMap.uiSettings.isZoomControlsEnabled = true
    googleMap.uiSettings.isMapToolbarEnabled = false
    googleMap.uiSettings.isMyLocationButtonEnabled = true
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Barcelone,17f))
    setUpClusterer()
    setUpMap()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    dashboardViewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_map, container, false)
    return root
  }

  fun getDataStation() {
    val activity: MainActivity? = activity as MainActivity?
    listStation = activity?.getStationList()!!
  }

  private fun setUpMap() {

    if (ActivityCompat.checkSelfPermission(requireActivity(),
        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(requireActivity(),
        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
      return
    }
    googleMap.isMyLocationEnabled = true

    fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->

      if (location != null) {
        lastLocation = location
        val currentLatLng = LatLng(location.latitude, location.longitude)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17f))
      }
    }
  }

  override fun onClusterItemClick(item: mapItem?): Boolean {
    val intent = Intent(requireActivity(), StationDetailActivity::class.java)
    if (item != null) {
      intent.putExtra("street_name", item.title)
      item.snippet?.let { intent.putExtra("id", it) }
    }
    startActivity(intent)
    return true
  }

  private fun setUpClusterer() {
    // Position the map.
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Barcelone, 17f))

    // Initialize the manager with the context and the map.
    // (Activity extends context, so we can pass 'this' in the constructor.)
    clusterManager = ClusterManager(context, googleMap)
    clusterManager!!.setOnClusterItemClickListener(this)

    // Point the map's listeners at the listeners implemented by the cluster
    // manager.
    googleMap.setOnCameraIdleListener(clusterManager)
    googleMap.setOnMarkerClickListener(clusterManager)

    // Add cluster items (markers) to the cluster manager.
    addItems()
  }

  private fun addItems() {
    // Add ten cluster items in close proximity, for purposes of this example.
    for (station in listStation) {
      val offsetItem =
        mapItem(station.lat.toDouble(),station.lon.toDouble(),station.street_name, station.id.toString())
      clusterManager.addItem(offsetItem)
    }
  }


}