package uz.coderodilov.kattabozortest.api

import retrofit2.Call
import retrofit2.http.GET
import uz.coderodilov.kattabozortest.models.Offers

/* 
* Created by Coder Odilov on 26/07/2023
*/

interface ApiService {
    @GET("hh/test/api/v1/offers")
    fun getDevices() : Call<Offers>
}