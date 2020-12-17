package com.stylish07.mycapstoneemgc

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stylish07.mycapstoneemgc.databinding.ActivitySelectBinding

class SelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activitySelectBinding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(activitySelectBinding.root)

        val intent = Intent(this, MapsActivity::class.java)
        activitySelectBinding.openMap.setOnClickListener{ startActivity(intent) }

        val intent2 = Intent(Intent.ACTION_VIEW, Uri.parse("http://portal.nemc.or.kr/medi_info/dashboards/dash_total_emer_org_popup_for_egen.do"))
        activitySelectBinding.openWeb.setOnClickListener { startActivity(intent2) }
    }


}