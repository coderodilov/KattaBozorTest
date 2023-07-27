package uz.coderodilov.kattabozortest.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.coderodilov.kattabozortest.repository.StoreRepository
import uz.coderodilov.kattabozortest.utils.NetworkStateListener
import javax.inject.Inject

/* 
* Created by Coder Odilov on 26/07/2023
*/

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val repository: StoreRepository,
    networkStateListener : NetworkStateListener) : ViewModel() {

    fun getDevices() { repository.getDevices() }

    val  devices = repository.getDevices()
    val networkState = networkStateListener

}