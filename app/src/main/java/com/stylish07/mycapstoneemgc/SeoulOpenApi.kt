package com.stylish07.mycapstoneemgc

import com.stylish07.mycapstoneemgc.data.Hospital
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class SeoulOpenApi {
    companion object {
        val DOMAIN = "http://openapi.seoul.go.kr:8088/"
        val API_KEY = "4c6767444d73747236356a4d485579"
    }
}

interface SeoulOpenService {
    @GET("{api_key}/json/TvEmgcHospitalInfo/1/200")
    fun getHospital(@Path("api_key") key: String): Call<Hospital>
}