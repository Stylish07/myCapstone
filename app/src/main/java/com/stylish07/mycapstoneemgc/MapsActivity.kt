package com.stylish07.mycapstoneemgc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.stylish07.mycapstoneemgc.data.Hospital
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        loadHospitals()
    }

    fun loadHospitals() {
        val retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val seoulOpenService = retrofit.create(SeoulOpenService::class.java)

        seoulOpenService
            .getHospital(SeoulOpenApi.API_KEY)
            .enqueue(object : Callback<Hospital> {
                override fun onResponse(call: Call<Hospital>, response: Response<Hospital>) {
                    showHospitals(response.body() as Hospital)
                }

                override fun onFailure(call: Call<Hospital>, t: Throwable) {
                    Toast.makeText(baseContext
                    , "서버에서 데이터를 가져올 수 없습니다."
                    , Toast.LENGTH_LONG).show()
                }
            })
    }

    fun showHospitals(hospitals: Hospital) {
        val latLngBounds = LatLngBounds.builder()

        for (hos in hospitals.TvEmgcHospitalInfo.row) {
            val position = LatLng(hos.WGS84LAT.toDouble(), hos.WGS84LON.toDouble())

            val marker = MarkerOptions().position(position).title(hos.DUTYNAME)

            mMap.addMarker(marker)

            latLngBounds.include(marker.position)
        }
        val bounds = latLngBounds.build()
        val padding = 1
        val updated = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        mMap.moveCamera(updated)
    }

}