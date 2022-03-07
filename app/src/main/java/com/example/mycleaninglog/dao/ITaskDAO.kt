package com.example.mycleaninglog.dao

import com.example.mycleaninglog.dto.Task

interface ITaskDAO {

    fun getTasks() : Call<ArrayList<Task>>
}