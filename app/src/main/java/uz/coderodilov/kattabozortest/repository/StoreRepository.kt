package uz.coderodilov.kattabozortest.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.coderodilov.kattabozortest.api.ApiService
import uz.coderodilov.kattabozortest.models.Offers
import uz.coderodilov.kattabozortest.models.Device
import javax.inject.Inject

/* 
* Created by Coder Odilov on 26/07/2023
*/

class StoreRepository @Inject constructor(private val apiService: ApiService){
    fun getDevices() : MutableLiveData<ArrayList<Device>>{
        val listOfDevice = MutableLiveData<ArrayList<Device>>()

        apiService.getDevices().enqueue(object : Callback<Offers>{
           override fun onResponse(call: Call<Offers>, response: Response<Offers>) {
               listOfDevice.postValue(response.body()?.offers as ArrayList<Device>)
           }

            override fun onFailure(call: Call<Offers>, t: Throwable) {
                t.printStackTrace()
            }

       })
        Log.d("TTT", listOfDevice.value.toString())
       return listOfDevice
    }
}