package uz.coderodilov.kattabozortest.repository

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.coderodilov.kattabozortest.api.ApiService
import uz.coderodilov.kattabozortest.models.Offers
import uz.coderodilov.kattabozortest.models.Device
import uz.coderodilov.kattabozortest.utils.NetworkResult
import uz.coderodilov.kattabozortest.utils.NetworkStatus
import javax.inject.Inject

/* 
* Created by Coder Odilov on 26/07/2023
*/

class StoreRepository @Inject constructor(private val apiService: ApiService) {

    private val listOfDevice = MutableLiveData<NetworkResult<List<Device>>>()

    fun getDevices(): MutableLiveData<NetworkResult<List<Device>>> {

        listOfDevice.postValue(NetworkResult.loading())

        apiService.getDevices().enqueue(object : Callback<Offers> {
            override fun onResponse(call: Call<Offers>, response: Response<Offers>) {
                listOfDevice.postValue(
                    NetworkResult.success(response.body()?.offers)
                )
            }

            override fun onFailure(call: Call<Offers>, t: Throwable) {
                listOfDevice.postValue(NetworkResult.error(t.toString()))
            }

        })

        return listOfDevice
    }
}