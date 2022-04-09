package com.example.mycleaninglog.service

import com.example.mycleaninglog.RetrofitClientInstance
import com.example.mycleaninglog.dao.ICleanDAO
import com.example.mycleaninglog.dto.cleaningTask
import com.example.mycleaninglog.dto.myRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class RoomService {
    suspend fun fetchRooms() : List<myRoom>? {
        return withContext(Dispatchers.IO) {
            val service = RetrofitClientInstance.retrofitInstance?.create(ICleanDAO::class.java)
            val products = async { service?.getAllRooms() }
            return@withContext products.await()?.awaitResponse()?.body()
        }
    }
}