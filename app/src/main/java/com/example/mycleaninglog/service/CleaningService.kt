package com.example.mycleaninglog.service

import com.example.mycleaninglog.RetrofitClientInstance
import com.example.mycleaninglog.dao.ICleanDAO
import com.example.mycleaninglog.dto.cleaningTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class CleaningService {
    suspend fun fetchProducts() : List<cleaningTask>? {
        return withContext(Dispatchers.IO) {
            val service = RetrofitClientInstance.retrofitInstance?.create(ICleanDAO::class.java)
            val products = async { service?.getAllTasks() }
            return@withContext products.await()?.awaitResponse()?.body()
        }
    }
}