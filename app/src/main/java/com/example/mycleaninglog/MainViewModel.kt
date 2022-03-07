package com.example.mycleaninglog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycleaninglog.dto.myRoom

class MainViewModel:ViewModel() {
    var myRooms: MutableLiveData<List<myRoom>> = MutableLiveData<List<myRoom>>()

}