package com.example.mycleaninglog.dao

import com.example.mycleaninglog.dto.cleaningTask
import retrofit2.Call

abstract class ICleanDAO {
    abstract fun getAllTasks(): Call<ArrayList<cleaningTask>>
}