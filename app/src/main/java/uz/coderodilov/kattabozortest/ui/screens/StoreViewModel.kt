package uz.coderodilov.kattabozortest.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.coderodilov.kattabozortest.repository.StoreRepository
import javax.inject.Inject

/* 
* Created by Coder Odilov on 26/07/2023
*/
@HiltViewModel
class StoreViewModel @Inject constructor(repository: StoreRepository) : ViewModel() {
    val  devices = repository.getDevices()
}