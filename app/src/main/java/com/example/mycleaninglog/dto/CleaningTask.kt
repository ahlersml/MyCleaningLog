package com.example.mycleaninglog.dto

data class cleaningTask(var cleaningTaskName:String = "", var cleaningTaskId:String = "", var uniqueID:String = ""){

    override fun toString(): String {
        return "$cleaningTaskName"
    }
}
