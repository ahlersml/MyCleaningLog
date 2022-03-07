package com.example.mycleaninglog.dao

import com.example.mycleaninglog.dto.cleaningTask

interface ITaskDAO {

    fun getTasks() : Call<ArrayList<Task>>
}