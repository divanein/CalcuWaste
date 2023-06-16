package com.alvintio.calcuwaste.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvintio.calcuwaste.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.alvintio.calcuwaste.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val surabaya = LatLng(-7.2754417, 112.6302805)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(surabaya))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(surabaya, 10f))

        val bsm = LatLng(-7.2750862, 112.6302801)
        mMap.addMarker(MarkerOptions().position(bsm).title("Bank Sampah Mandiri"))

        val bssh = LatLng(-7.2219054, 112.7234818)
        mMap.addMarker(MarkerOptions().position(bssh).title("Bank Sampah Sejahtera Hangtuah"))

        val bskkp = LatLng(-7.2591977, 112.7555591)
        mMap.addMarker(MarkerOptions().position(bskkp).title("Bank Sampah Karang Kedempel Pacarkeling"))

        val bsms = LatLng(-7.2626984, 112.7247442)
        mMap.addMarker(MarkerOptions().position(bsms).title("Bank Sampah Mekar Sari"))

        val bss = LatLng(-7.2636306, 112.707894)
        mMap.addMarker(MarkerOptions().position(bss).title("Bank Sampah Simponi"))

        val bsbm = LatLng(-7.3456761, 112.8048941)
        mMap.addMarker(MarkerOptions().position(bsbm).title("Bank Sampah Bintang Mangrove"))

        val gbb = LatLng(-7.2683477, 112.7310791)
        mMap.addMarker(MarkerOptions().position(gbb).title("Garbage Bank Bed"))

        val bsp = LatLng(-7.2549487, 112.7091791)
        mMap.addMarker(MarkerOptions().position(bsp).title("Bank Sampah Prima"))

        val bsl = LatLng(-7.2376657, 112.7517991)
        mMap.addMarker(MarkerOptions().position(bsl).title("Bank Sampah Lestari"))

        val bsmj = LatLng(-7.2729787, 112.7329181)
        mMap.addMarker(MarkerOptions().position(bsmj).title("Bank Sampah Mekar Jaya"))

        val bssr = LatLng(-7.2510397, 112.7164111)
        mMap.addMarker(MarkerOptions().position(bssr).title("Bank Sampah Sri Ratu"))

        val bsmbd = LatLng(-7.2394227, 112.7131187)
        mMap.addMarker(MarkerOptions().position(bsmbd).title("Bank Sampah Makmur Bersama Dupak"))

        val bsb = LatLng(-7.2555797, 112.7581731)
        mMap.addMarker(MarkerOptions().position(bsb).title("Bank Sampah Bertiga"))

        val bssp = LatLng(-7.2568954, 112.7332187)
        mMap.addMarker(MarkerOptions().position(bssp).title("Bank Sampah Spenfora"))

        val bssa = LatLng(-7.2717331, 112.7209777)
        mMap.addMarker(MarkerOptions().position(bssa).title("Bank Sampah Sampurna"))

        val bsmas = LatLng(-7.2483312, 112.7212977)
        mMap.addMarker(MarkerOptions().position(bsmas).title("Bank Sampah Makmur 5 Surabaya"))
    }
}