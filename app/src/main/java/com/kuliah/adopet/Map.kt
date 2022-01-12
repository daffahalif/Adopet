package com.kuliah.adopet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*

class Map : AppCompatActivity() {
    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val extras = intent.extras
        var latitude = extras?.getString("x")
        var longitude = extras?.getString("y")

        mapFragment = map as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            var location1 = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
            googleMap.addMarker(MarkerOptions().position(location1).title("Owner Location"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,15f))
        })

        backBtn.setOnClickListener {
            finish()
        }
    }
}